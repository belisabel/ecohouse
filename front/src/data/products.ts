export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  image: string;
  category: string;
  impact: {
    carbonFootprint: number; // kg CO2
    recyclable: number; // percentage
    waterUsage: number; // liters
    transportDistance: number; // km
  };
  certifications: string[];
  materials: string[];
}

export const products: Product[] = [
  {
    id: "1",
    name: "Camiseta Orgánica de Algodón",
    description: "Camiseta 100% algodón orgánico certificado GOTS. Producción ética y sostenible.",
    price: 29.99,
    image: "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500",
    category: "Ropa",
    impact: {
      carbonFootprint: 2.1,
      recyclable: 95,
      waterUsage: 120,
      transportDistance: 500,
    },
    certifications: ["GOTS", "Fair Trade", "Carbon Neutral"],
    materials: ["Algodón Orgánico 100%"],
  },
  {
    id: "2",
    name: "Botella Reutilizable de Acero",
    description: "Botella de acero inoxidable de doble pared. Mantiene bebidas frías 24h y calientes 12h.",
    price: 24.99,
    image: "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=500",
    category: "Hogar",
    impact: {
      carbonFootprint: 1.5,
      recyclable: 100,
      waterUsage: 50,
      transportDistance: 800,
    },
    certifications: ["BPA Free", "Recyclable"],
    materials: ["Acero Inoxidable 304"],
  },
  {
    id: "3",
    name: "Mochila de Material Reciclado",
    description: "Mochila resistente al agua fabricada con botellas PET recicladas.",
    price: 49.99,
    image: "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500",
    category: "Accesorios",
    impact: {
      carbonFootprint: 3.8,
      recyclable: 85,
      waterUsage: 200,
      transportDistance: 1200,
    },
    certifications: ["GRS", "Vegan", "Ocean Plastic"],
    materials: ["rPET 100%", "Poliéster Reciclado"],
  },
  {
    id: "4",
    name: "Kit de Utensilios de Bambú",
    description: "Set de cubiertos reutilizables de bambú natural con estuche de algodón.",
    price: 15.99,
    image: "https://images.unsplash.com/photo-1606857521015-7f9fcf423740?w=500",
    category: "Hogar",
    impact: {
      carbonFootprint: 0.8,
      recyclable: 100,
      waterUsage: 30,
      transportDistance: 600,
    },
    certifications: ["FSC", "Compostable", "Biodegradable"],
    materials: ["Bambú Natural", "Algodón Orgánico"],
  },
  {
    id: "5",
    name: "Jabón Natural Artesanal",
    description: "Jabón elaborado a mano con ingredientes orgánicos y aceites esenciales.",
    price: 8.99,
    image: "https://images.unsplash.com/photo-1600857544200-b1071ce73e00?w=500",
    category: "Cuidado Personal",
    impact: {
      carbonFootprint: 0.3,
      recyclable: 100,
      waterUsage: 15,
      transportDistance: 200,
    },
    certifications: ["Organic", "Cruelty Free", "Vegan"],
    materials: ["Aceites Vegetales", "Aceites Esenciales"],
  },
  {
    id: "6",
    name: "Zapatillas Sostenibles",
    description: "Zapatillas deportivas fabricadas con materiales reciclados y suela de caucho natural.",
    price: 79.99,
    image: "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500",
    category: "Calzado",
    impact: {
      carbonFootprint: 5.2,
      recyclable: 75,
      waterUsage: 300,
      transportDistance: 1500,
    },
    certifications: ["Recycled Materials", "Carbon Offset"],
    materials: ["rPET", "Caucho Natural", "Algodón Reciclado"],
  },
];
