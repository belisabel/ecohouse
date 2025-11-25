-- V001__Create_impact_reports_table.sql
-- Migration para crear la tabla impact_reports (MySQL compatible con Flyway)

CREATE TABLE IF NOT EXISTS impact_reports (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    total_co2_saved DECIMAL(10,3) DEFAULT 0.000,
    total_co2_footprint DECIMAL(10,3) DEFAULT 0.000,
    total_orders INTEGER DEFAULT 0,
    eco_points_earned INTEGER DEFAULT 0,
    total_amount_spent DECIMAL(12,2) DEFAULT 0.00,
    sustainable_choices_count INTEGER DEFAULT 0,
    report_type VARCHAR(20) NOT NULL DEFAULT 'ON_DEMAND',
    sustainable_product_ids TEXT,
    category_breakdown TEXT,
    monthly_trend TEXT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Índices para optimizar consultas (MySQL syntax)
CREATE INDEX idx_impact_reports_customer_id ON impact_reports(customer_id);
CREATE INDEX idx_impact_reports_date_range ON impact_reports(start_date, end_date);
CREATE INDEX idx_impact_reports_type ON impact_reports(report_type);
CREATE INDEX idx_impact_reports_created_at ON impact_reports(created_at);
CREATE INDEX idx_impact_reports_active ON impact_reports(is_active);

-- Índice compuesto para consultas frecuentes
CREATE INDEX idx_impact_reports_customer_active_created ON impact_reports(customer_id, is_active, created_at);

-- Índice para búsquedas por período (sin WHERE, MySQL no soporta partial indexes)
CREATE INDEX idx_impact_reports_customer_period ON impact_reports(customer_id, report_type, start_date, end_date);

-- Constraints adicionales (MySQL soporta CHECK a partir de versiones recientes)
ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_date_range
CHECK (start_date <= end_date);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_co2_positive
CHECK (total_co2_saved >= 0 AND total_co2_footprint >= 0);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_orders_positive
CHECK (total_orders >= 0);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_points_positive
CHECK (eco_points_earned >= 0);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_amount_positive
CHECK (total_amount_spent >= 0);

-- Comentarios para documentación
ALTER TABLE impact_reports COMMENT = 'Tabla para almacenar reportes de impacto ambiental de customers';

-- NOTA: evitamos crear triggers con DELIMITER porque Flyway ejecuta las instrucciones vía JDBC y los delimitadores no se aceptan.
-- En su lugar usamos la columna updated_at con ON UPDATE CURRENT_TIMESTAMP (ya definida arriba).

-- Datos de ejemplo para testing (opcional)
INSERT INTO impact_reports (
    customer_id,
    start_date,
    end_date,
    total_co2_saved,
    total_co2_footprint,
    total_orders,
    eco_points_earned,
    total_amount_spent,
    sustainable_choices_count,
    report_type
) VALUES
(1, '2024-01-01 00:00:00', '2024-01-31 23:59:59', 15.500, 25.750, 8, 120, 450.00, 5, 'MONTHLY'),
(1, '2024-02-01 00:00:00', '2024-02-29 23:59:59', 22.300, 30.200, 12, 180, 680.50, 8, 'MONTHLY'),
(2, '2024-01-01 00:00:00', '2024-01-31 23:59:59', 8.200, 18.400, 5, 75, 320.00, 3, 'MONTHLY');
