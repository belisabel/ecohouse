-- V001__Create_impact_reports_table_mysql.sql
-- Versión adaptada a MySQL 8+ para crear la tabla impact_reports

CREATE TABLE IF NOT EXISTS impact_reports (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_co2_saved DECIMAL(10,3) DEFAULT 0.000,
    total_co2_footprint DECIMAL(10,3) DEFAULT 0.000,
    total_orders INT DEFAULT 0,
    eco_points_earned INT DEFAULT 0,
    total_amount_spent DECIMAL(12,2) DEFAULT 0.00,
    sustainable_choices_count INT DEFAULT 0,
    report_type VARCHAR(20) NOT NULL DEFAULT 'ON_DEMAND',
    sustainable_product_ids TEXT,
    category_breakdown TEXT,
    monthly_trend TEXT,
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices
CREATE INDEX idx_impact_reports_customer_id ON impact_reports(customer_id);
CREATE INDEX idx_impact_reports_date_range ON impact_reports(start_date, end_date);
CREATE INDEX idx_impact_reports_type ON impact_reports(report_type);
CREATE INDEX idx_impact_reports_created_at ON impact_reports(created_at);
CREATE INDEX idx_impact_reports_active ON impact_reports(is_active);

CREATE INDEX idx_impact_reports_customer_active_created
ON impact_reports(customer_id, is_active, created_at);

CREATE INDEX idx_impact_reports_customer_period
ON impact_reports(customer_id, report_type, start_date, end_date);

-- Constraints (MySQL 8+)
ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_date_range CHECK (start_date <= end_date);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_co2_positive CHECK (total_co2_saved >= 0 AND total_co2_footprint >= 0);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_orders_positive CHECK (total_orders >= 0);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_points_positive CHECK (eco_points_earned >= 0);

ALTER TABLE impact_reports
ADD CONSTRAINT chk_impact_reports_amount_positive CHECK (total_amount_spent >= 0);

-- Trigger para actualizar updated_at
DROP TRIGGER IF EXISTS trigger_update_impact_reports_updated_at;
DELIMITER $$
CREATE TRIGGER trigger_update_impact_reports_updated_at
BEFORE UPDATE ON impact_reports
FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END$$
DELIMITER ;

-- Datos de ejemplo (opcional)
INSERT IGNORE INTO impact_reports (
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
(1, '2024-01-01', '2024-01-31', 15.500, 25.750, 8, 120, 450.00, 5, 'MONTHLY'),
(1, '2024-02-01', '2024-02-29', 22.300, 30.200, 12, 180, 680.50, 8, 'MONTHLY'),
(2, '2024-01-01', '2024-01-31', 8.200, 18.400, 5, 75, 320.00, 3, 'MONTHLY');

