import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Cell } from "recharts";
import { Product } from "../../data/products";

interface ImpactChartProps {
  product: Product;
}

export const ImpactChart = ({ product }: ImpactChartProps) => {
  const data = [
    {
      name: "Huella de Carbono",
      value: product.impact.carbonFootprint,
      unit: "kg CO₂",
      color: "oklch(0.55 0.14 150)", // Verde primario
    },
    {
      name: "Consumo de Agua",
      value: product.impact.waterUsage / 10, // Scale down for better visualization
      unit: `${product.impact.waterUsage}L`,
      color: "oklch(0.68 0.13 80)", // Amarillo/naranja
    },
    {
      name: "Reciclabilidad",
      value: product.impact.recyclable / 10, // Scale to 0-10
      unit: `${product.impact.recyclable}%`,
      color: "oklch(0.35 0.08 160)", // Verde claro
    },
  ];

  return (
    <Card className="border-border bg-card">
      <CardHeader>
        <CardTitle className="text-foreground">Comparativa de Impacto</CardTitle>
      </CardHeader>
      <CardContent>
        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={data}>
            <CartesianGrid 
              strokeDasharray="3 3" 
              stroke="hsl(var(--border))"
              opacity={0.3}
            />
            <XAxis 
              dataKey="name" 
              tick={{ fill: "hsl(var(--muted-foreground))", fontSize: 12 }}
              stroke="hsl(var(--border))"
            />
            <YAxis 
              tick={{ fill: "hsl(var(--muted-foreground))", fontSize: 12 }}
              stroke="hsl(var(--border))"
            />
            <Tooltip
              contentStyle={{
                backgroundColor: "oklch(0.98 0.01 120)",
                border: "1px solid hsl(var(--border))",
                borderRadius: "var(--radius)",
                color: "hsl(var(--foreground))",
              }}
              labelStyle={{
                color: "hsl(var(--foreground))",
                fontWeight: 600,
              }}
              formatter={(value, name, props) => [
                props.payload.unit, 
                props.payload.name
              ]}
            />
            <Bar 
              dataKey="value" 
              radius={[8, 8, 0, 0]}
              maxBarSize={80}
            >
              {data.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={entry.color} />
              ))}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
        <p className="text-xs text-muted-foreground text-center mt-4">
          * Valores escalados para mejor visualización
        </p>
      </CardContent>
    </Card>
  );
};