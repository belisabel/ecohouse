import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Leaf, Recycle, Droplets, Truck, Award } from "lucide-react";
import { Product } from "@/data/products";

interface ImpactMetricsProps {
  product: Product;
}

export const ImpactMetrics = ({ product }: ImpactMetricsProps) => {
  const getImpactLevel = (value: number, type: "carbon" | "recyclable" | "water") => {
    if (type === "carbon") {
      if (value < 2) return { level: "Bajo", className: "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400 border-green-200 dark:border-green-800" };
      if (value < 4) return { level: "Medio", className: "bg-yellow-100 text-yellow-800 dark:bg-yellow-900/30 dark:text-yellow-400 border-yellow-200 dark:border-yellow-800" }; 
      return { level: "Alto", className: "bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400 border-red-200 dark:border-red-800" };
    }
    if (type === "recyclable") {
      if (value > 80) return { level: "Alto", className: "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400 border-green-200 dark:border-green-800" };
      if (value > 50) return { level: "Medio", className: "bg-yellow-100 text-yellow-800 dark:bg-yellow-900/30 dark:text-yellow-400 border-yellow-200 dark:border-yellow-800" };
      return { level: "Bajo", className: "bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400 border-red-200 dark:border-red-800" };
    }
    if (type === "water") {
      if (value < 100) return { level: "Bajo", className: "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400 border-green-200 dark:border-green-800" };
      if (value < 250) return { level: "Medio", className: "bg-yellow-100 text-yellow-800 dark:bg-yellow-900/30 dark:text-yellow-400 border-yellow-200 dark:border-yellow-800" };
      return { level: "Alto", className: "bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400 border-red-200 dark:border-red-800" };
    }
    return { level: "Medio", className: "bg-yellow-100 text-yellow-800 dark:bg-yellow-900/30 dark:text-yellow-400 border-yellow-200 dark:border-yellow-800" };
  };

  const carbonImpact = getImpactLevel(product.impact.carbonFootprint, "carbon");
  const recyclableImpact = getImpactLevel(product.impact.recyclable, "recyclable");
  const waterImpact = getImpactLevel(product.impact.waterUsage, "water");

  return (
    <div className="space-y-8">
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Leaf className="h-5 w-5 text-primary" />
            Impacto Ambiental
          </CardTitle>
        </CardHeader>
        <CardContent className="space-y-6">
          <div className="grid md:grid-cols-2 gap-4">
            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <span className="text-sm font-medium flex items-center gap-2">
                  <Leaf className="h-4 w-4" />
                  Huella de Carbono
                </span>
                <Badge variant="outline" className={carbonImpact.className}>
                  {carbonImpact.level}
                </Badge>
              </div>
              <p className="text-2xl font-bold">{product.impact.carbonFootprint} kg CO₂</p>
              <p className="text-xs text-muted-foreground">
                Por unidad producida y transportada
              </p>
            </div>

            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <span className="text-sm font-medium flex items-center gap-2">
                  <Recycle className="h-4 w-4" />
                  Reciclabilidad
                </span>
                <Badge variant="outline" className={recyclableImpact.className}>
                  {recyclableImpact.level}
                </Badge>
              </div>
              <p className="text-2xl font-bold">{product.impact.recyclable}%</p>
              <p className="text-xs text-muted-foreground">
                Materiales reciclables al final de vida útil
              </p>
            </div>

            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <span className="text-sm font-medium flex items-center gap-2">
                  <Droplets className="h-4 w-4" />
                  Consumo de Agua
                </span>
                <Badge variant="outline" className={waterImpact.className}>
                  {waterImpact.level}
                </Badge>
              </div>
              <p className="text-2xl font-bold">{product.impact.waterUsage} L</p>
              <p className="text-xs text-muted-foreground">
                Durante todo el proceso de fabricación
              </p>
            </div>

            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <span className="text-sm font-medium flex items-center gap-2">
                  <Truck className="h-4 w-4" />
                  Distancia de Transporte
                </span>
              </div>
              <p className="text-2xl font-bold">{product.impact.transportDistance} km</p>
              <p className="text-xs text-muted-foreground">
                Desde fabricación hasta almacén
              </p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Award className="h-5 w-5 text-primary" />
            Certificaciones
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex flex-wrap gap-2">
            {product.certifications.map((cert) => (
              <Badge key={cert} variant="secondary" className="text-sm">
                {cert}
              </Badge>
            ))}
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Materiales</CardTitle>
        </CardHeader>
        <CardContent>
          <ul className="space-y-2">
            {product.materials.map((material, index) => (
              <li key={index} className="flex items-center gap-2 text-sm">
                <div className="h-2 w-2 rounded-full bg-primary" />
                {material}
              </li>
            ))}
          </ul>
        </CardContent>
      </Card>
    </div>
  );
};