import { Link } from "react-router-dom";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Leaf, Droplets, Recycle } from "lucide-react";
import { Product } from "@/data/products";

interface ProductCardProps {
  product: Product;
}

export const ProductCard = ({ product }: ProductCardProps) => {
  return (
    <Link to={`/product/${product.id}`}>
      <Card className="group overflow-hidden transition-all hover:shadow-lg hover:scale-[1.02] border-border/50">
        <div className="relative aspect-square overflow-hidden bg-muted">
          <img
            src={product.image}
            alt={product.name}
            className="h-full w-full object-cover transition-transform duration-300 group-hover:scale-110"
          />
          <Badge className="absolute top-3 right-3 bg-eco-badge text-eco-badge-foreground">
            <Leaf className="mr-1 h-3 w-3" />
            Eco
          </Badge>
        </div>
        <CardContent className="p-4">
          <p className="text-xs text-muted-foreground mb-1">{product.category}</p>
          <h3 className="font-semibold text-lg mb-2 line-clamp-1">{product.name}</h3>
          <p className="text-sm text-muted-foreground mb-3 line-clamp-2">
            {product.description}
          </p>
          
          <div className="flex gap-4 text-xs">
            <div className="flex items-center gap-1">
              <Leaf className="h-3 w-3 text-impact-low" />
              <span>{product.impact.carbonFootprint}kg CO₂</span>
            </div>
            <div className="flex items-center gap-1">
              <Recycle className="h-3 w-3 text-impact-low" />
              <span>{product.impact.recyclable}%</span>
            </div>
            <div className="flex items-center gap-1">
              <Droplets className="h-3 w-3 text-primary" />
              <span>{product.impact.waterUsage}L</span>
            </div>
          </div>
        </CardContent>
        <CardFooter className="p-4 pt-0 flex items-center justify-between">
          <span className="text-2xl font-bold text-primary">€{product.price.toFixed(2)}</span>
          <Button variant="default" size="sm">Ver Detalles</Button>
        </CardFooter>
      </Card>
    </Link>
  );
};
