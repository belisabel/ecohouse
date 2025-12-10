package com.EcoHouse.config;

import com.EcoHouse.category.model.Category;
import com.EcoHouse.category.repository.CategoryRepository;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.model.EnvironmentalData;
import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.repository.BrandRepository;
import com.EcoHouse.product.repository.CertificationRepository;
import com.EcoHouse.product.repository.ProductRepository;
import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.model.UserType;
import com.EcoHouse.user.repository.CustomerRepository;
import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderItem;
import com.EcoHouse.order.model.OrderStatus;
import com.EcoHouse.order.model.Payment;
import com.EcoHouse.order.model.ShippingAddress;
import com.EcoHouse.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Cargador de datos iniciales para la aplicación.
 * Se ejecuta una sola vez al iniciar la aplicación.
 * Carga marcas, categorías y certificaciones específicas de ropa ecológica.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CertificationRepository certificationRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadBrands();
        loadCategories();
        loadCertifications();
        loadCustomers();
        loadProducts();
        loadOrders();
        log.info("✅ Datos iniciales cargados exitosamente");
    }

    /**
     * Carga marcas de ropa ecológica predefinidas
     */
    private void loadBrands() {
        if (brandRepository.count() == 0) {
            log.info("👕 Cargando marcas de ropa ecológica...");

            Brand[] brands = {
                    Brand.builder()
                            .name("Patagonia")
                            .description("Ropa outdoor sostenible y activismo ambiental")
                            .imageUrl("https://example.com/patagonia-logo.png")
                            .country("Estados Unidos")
                            .build(),

                    Brand.builder()
                            .name("EcoAlf")
                            .description("Primera marca de moda sostenible del mundo con materiales reciclados")
                            .imageUrl("https://example.com/ecoalf-logo.png")
                            .country("España")
                            .build(),

                    Brand.builder()
                            .name("Tentree")
                            .description("Por cada compra plantan 10 árboles, ropa casual sostenible")
                            .imageUrl("https://example.com/tentree-logo.png")
                            .country("Canadá")
                            .build(),

                    Brand.builder()
                            .name("People Tree")
                            .description("Pionera en moda ética y comercio justo desde 1991")
                            .imageUrl("https://example.com/peopletree-logo.png")
                            .country("Reino Unido")
                            .build(),

                    Brand.builder()
                            .name("Reformation")
                            .description("Moda femenina con huella de carbono neutral")
                            .imageUrl("https://example.com/reformation-logo.png")
                            .country("Estados Unidos")
                            .build(),

                    Brand.builder()
                            .name("Veja")
                            .description("Zapatillas ecológicas de comercio justo y materiales orgánicos")
                            .imageUrl("https://example.com/veja-logo.png")
                            .country("Francia")
                            .build(),

                    Brand.builder()
                            .name("Thought")
                            .description("Ropa de algodón orgánico y bambú para toda la familia")
                            .imageUrl("https://example.com/thought-logo.png")
                            .country("Reino Unido")
                            .build(),

                    Brand.builder()
                            .name("Nudie Jeans")
                            .description("Jeans 100% algodón orgánico con reparaciones gratuitas de por vida")
                            .imageUrl("https://example.com/nudiejeans-logo.png")
                            .country("Suecia")
                            .build(),

                    Brand.builder()
                            .name("Organic Basics")
                            .description("Ropa interior y basics sostenibles de producción ética")
                            .imageUrl("https://example.com/organicbasics-logo.png")
                            .country("Dinamarca")
                            .build(),

                    Brand.builder()
                            .name("Pangaia")
                            .description("Innovación en materiales científicos y moda sostenible")
                            .imageUrl("https://example.com/pangaia-logo.png")
                            .country("Reino Unido")
                            .build()
            };

            brandRepository.saveAll(java.util.Arrays.asList(brands));
            log.info("✅ {} marcas de ropa cargadas", brands.length);
        } else {
            log.info("⏭️ Las marcas ya están cargadas, omitiendo...");
        }
    }

    /**
     * Carga categorías de ropa ecológica predefinidas
     */
    private void loadCategories() {
        if (categoryRepository.count() == 0) {
            log.info("📂 Cargando categorías de ropa...");

            Category[] categories = {
                    Category.builder()
                            .name("Camisetas y Polos")
                            .description("Camisetas, polos y tops de algodón orgánico y materiales sostenibles")
                            .iconUrl("👕")
                            .build(),

                    Category.builder()
                            .name("Pantalones y Jeans")
                            .description("Pantalones, jeans y leggins de producción ética y telas ecológicas")
                            .iconUrl("👖")
                            .build(),

                    Category.builder()
                            .name("Vestidos y Faldas")
                            .description("Vestidos, faldas y prendas femeninas de moda sostenible")
                            .iconUrl("👗")
                            .build(),

                    Category.builder()
                            .name("Abrigos y Chaquetas")
                            .description("Chaquetas, abrigos y prendas de abrigo de materiales reciclados")
                            .iconUrl("🧥")
                            .build(),

                    Category.builder()
                            .name("Calzado Ecológico")
                            .description("Zapatos, zapatillas y sandalias de cuero vegano y materiales sostenibles")
                            .iconUrl("👟")
                            .build(),

                    Category.builder()
                            .name("Accesorios y Complementos")
                            .description("Bolsos, mochilas, cinturones y accesorios de moda ética")
                            .iconUrl("👜")
                            .build(),

                    Category.builder()
                            .name("Ropa Interior Sostenible")
                            .description("Ropa interior y lencería de algodón orgánico y bambú")
                            .iconUrl("🩲")
                            .build(),

                    Category.builder()
                            .name("Ropa Deportiva")
                            .description("Ropa deportiva y activewear de materiales reciclados y transpirables")
                            .iconUrl("🏃")
                            .build(),

                    Category.builder()
                            .name("Ropa de Bebé y Niños")
                            .description("Ropa infantil y de bebé de algodón orgánico y materiales hipoalergénicos")
                            .iconUrl("👶")
                            .build(),

                    Category.builder()
                            .name("Trajes de Baño Eco")
                            .description("Trajes de baño y ropa de playa de plásticos reciclados del océano")
                            .iconUrl("🩱")
                            .build()
            };

            categoryRepository.saveAll(java.util.Arrays.asList(categories));
            log.info("✅ {} categorías de ropa cargadas", categories.length);
        } else {
            log.info("⏭️ Las categorías ya están cargadas, omitiendo...");
        }
    }

    /**
     * Carga certificaciones textiles y de moda sostenible predefinidas
     */
    private void loadCertifications() {
        if (certificationRepository.count() == 0) {
            log.info("🏅 Cargando certificaciones textiles...");

            Certification[] certifications = {
                    Certification.builder()
                            .name("GOTS (Global Organic Textile Standard)")
                            .description("Estándar líder mundial para textiles fabricados con fibras orgánicas certificadas")
                            .issuedBy("Global Organic Textile Standard International")
                            .website("https://www.global-standard.org")
                            .build(),

                    Certification.builder()
                            .name("OEKO-TEX Standard 100")
                            .description("Garantiza que los textiles están libres de sustancias nocivas para la salud")
                            .issuedBy("OEKO-TEX Association")
                            .website("https://www.oeko-tex.com")
                            .build(),

                    Certification.builder()
                            .name("Fair Trade Certified")
                            .description("Garantiza prácticas laborales justas y comercio ético en la industria textil")
                            .issuedBy("Fair Trade USA")
                            .website("https://www.fairtradecertified.org")
                            .build(),

                    Certification.builder()
                            .name("Bluesign")
                            .description("Sistema de gestión que garantiza producción textil sostenible y segura")
                            .issuedBy("Bluesign Technologies AG")
                            .website("https://www.bluesign.com")
                            .build(),

                    Certification.builder()
                            .name("Cradle to Cradle Certified")
                            .description("Productos diseñados para la circularidad, seguros y sostenibles")
                            .issuedBy("Cradle to Cradle Products Innovation Institute")
                            .website("https://www.c2ccertified.org")
                            .build(),

                    Certification.builder()
                            .name("Better Cotton Initiative (BCI)")
                            .description("Programa de sostenibilidad para el cultivo de algodón a nivel global")
                            .issuedBy("Better Cotton Initiative")
                            .website("https://bettercotton.org")
                            .build(),

                    Certification.builder()
                            .name("Certified B Corporation")
                            .description("Empresas que cumplen altos estándares de desempeño social y ambiental")
                            .issuedBy("B Lab")
                            .website("https://www.bcorporation.net")
                            .build(),

                    Certification.builder()
                            .name("EU Ecolabel")
                            .description("Etiqueta ecológica europea para productos textiles de bajo impacto ambiental")
                            .issuedBy("Comisión Europea")
                            .website("https://ec.europa.eu/ecolabel")
                            .build(),

                    Certification.builder()
                            .name("Leather Working Group")
                            .description("Certificación de cuero producido con prácticas ambientales responsables")
                            .issuedBy("Leather Working Group")
                            .website("https://www.leatherworkinggroup.com")
                            .build(),

                    Certification.builder()
                            .name("SA8000 Social Accountability")
                            .description("Norma internacional de responsabilidad social en condiciones laborales")
                            .issuedBy("Social Accountability International")
                            .website("https://sa-intl.org")
                            .build()
            };

            certificationRepository.saveAll(java.util.Arrays.asList(certifications));
            log.info("✅ {} certificaciones textiles cargadas", certifications.length);
        } else {
            log.info("⏭️ Las certificaciones ya están cargadas, omitiendo...");
        }
    }

    /**
     * Carga 10 clientes de ejemplo predefinidos
     */
    private void loadCustomers() {
        if (customerRepository.count() == 0) {
            log.info("👥 Cargando clientes de ejemplo...");

            Customer[] customers = {
                    // Usuario BRAND_ADMIN para administración
                    Customer.builder()
                            .email("admin@ecohouse.com")
                            .firstName("Admin")
                            .lastName("EcoHouse")
                            .password(passwordEncoder.encode("Admin2024!"))
                            .userType(UserType.BRAND_ADMIN)
                            .phone("+34900000000")
                            .shippingAddress("Oficina Central EcoHouse, Madrid, España")
                            .billingAddress("Oficina Central EcoHouse, Madrid, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("ana.garcia@gmail.com")
                            .firstName("Ana")
                            .lastName("García")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34612345678")
                            .shippingAddress("Calle Mayor 123, Madrid, España")
                            .billingAddress("Calle Mayor 123, Madrid, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("carlos.lopez@gmail.com")
                            .firstName("Carlos")
                            .lastName("López")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34623456789")
                            .shippingAddress("Avenida Libertad 45, Barcelona, España")
                            .billingAddress("Avenida Libertad 45, Barcelona, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("maria.rodriguez@gmail.com")
                            .firstName("María")
                            .lastName("Rodríguez")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34634567890")
                            .shippingAddress("Plaza España 78, Valencia, España")
                            .billingAddress("Plaza España 78, Valencia, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("pedro.martinez@gmail.com")
                            .firstName("Pedro")
                            .lastName("Martínez")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34645678901")
                            .shippingAddress("Calle Sol 12, Sevilla, España")
                            .billingAddress("Calle Sol 12, Sevilla, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("laura.sanchez@gmail.com")
                            .firstName("Laura")
                            .lastName("Sánchez")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34656789012")
                            .shippingAddress("Avenida Constitución 34, Málaga, España")
                            .billingAddress("Avenida Constitución 34, Málaga, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("javier.fernandez@gmail.com")
                            .firstName("Javier")
                            .lastName("Fernández")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34667890123")
                            .shippingAddress("Calle Comercio 56, Bilbao, España")
                            .billingAddress("Calle Comercio 56, Bilbao, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("sofia.gomez@gmail.com")
                            .firstName("Sofía")
                            .lastName("Gómez")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34678901234")
                            .shippingAddress("Plaza Mayor 89, Zaragoza, España")
                            .billingAddress("Plaza Mayor 89, Zaragoza, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("diego.ruiz@gmail.com")
                            .firstName("Diego")
                            .lastName("Ruiz")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34689012345")
                            .shippingAddress("Calle Victoria 23, Granada, España")
                            .billingAddress("Calle Victoria 23, Granada, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("elena.torres@gmail.com")
                            .firstName("Elena")
                            .lastName("Torres")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34690123456")
                            .shippingAddress("Avenida Principal 67, Murcia, España")
                            .billingAddress("Avenida Principal 67, Murcia, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build(),

                    Customer.builder()
                            .email("jorge.vazquez@gmail.com")
                            .firstName("Jorge")
                            .lastName("Vázquez")
                            .password(passwordEncoder.encode("password123"))
                            .userType(UserType.CUSTOMER)
                            .phone("+34601234567")
                            .shippingAddress("Calle Real 90, Alicante, España")
                            .billingAddress("Calle Real 90, Alicante, España")
                            .carbonFootprint(0.0)
                            .isActive(true)
                            .createdAt(LocalDateTime.now())
                            .build()
            };

            customerRepository.saveAll(java.util.Arrays.asList(customers));
            log.info("✅ {} clientes cargados (incluyendo 1 BRAND_ADMIN)", customers.length);
        } else {
            log.info("⏭️ Los clientes ya están cargados, omitiendo...");
        }
    }

    /**
     * Carga 10 productos de ejemplo con datos ambientales
     */
    private void loadProducts() {
        if (productRepository.count() == 0) {
            log.info("🛍️ Cargando productos de ejemplo...");

            // Obtener marcas, categorías y certificaciones para relacionar con productos
            List<Brand> brands = brandRepository.findAll();
            List<Category> categories = categoryRepository.findAll();
            List<Certification> certifications = certificationRepository.findAll();

            if (brands.isEmpty() || categories.isEmpty() || certifications.isEmpty()) {
                log.warn("⚠️ No se pueden cargar productos sin marcas, categorías o certificaciones");
                return;
            }

            // Crear productos con sus datos ambientales
            Product product1 = new Product();
            product1.setName("Camiseta Orgánica Básica");
            product1.setDescription("Camiseta 100% algodón orgánico certificado GOTS, perfecta para uso diario");
            product1.setPrice(new BigDecimal("29.99"));
            product1.setImageUrl("https://example.com/camiseta-organica.jpg");
            product1.setStock(150);
            product1.setBrand(brands.get(0));
            product1.setCategory(categories.get(0));
            product1.setIsActive(true);

            EnvironmentalData env1 = new EnvironmentalData();
            env1.setCarbonFootprint(new BigDecimal("2.5"));
            env1.setMaterial("Algodón orgánico");
            env1.setCountryOfOrigin("India");
            env1.setEnergyConsumption(new BigDecimal("15.5"));
            env1.setRecyclablePercentage(new BigDecimal("95.0"));
            env1.setNotes("Teñido con tintes naturales, libre de químicos tóxicos");
            product1.setEnvironmentalData(env1);
            env1.setProduct(product1);

            Set<Certification> cert1 = new HashSet<>();
            cert1.add(certifications.get(0)); // GOTS
            cert1.add(certifications.get(1)); // OEKO-TEX
            product1.setCertifications(cert1);

            Product product2 = new Product();
            product2.setName("Jeans Reciclados Slim Fit");
            product2.setDescription("Jeans fabricados con algodón reciclado y poliéster reciclado de botellas PET");
            product2.setPrice(new BigDecimal("79.99"));
            product2.setImageUrl("https://example.com/jeans-reciclados.jpg");
            product2.setStock(100);
            product2.setBrand(brands.get(1));
            product2.setCategory(categories.get(1));
            product2.setIsActive(true);

            EnvironmentalData env2 = new EnvironmentalData();
            env2.setCarbonFootprint(new BigDecimal("8.5"));
            env2.setMaterial("60% algodón reciclado, 40% poliéster reciclado");
            env2.setCountryOfOrigin("España");
            env2.setEnergyConsumption(new BigDecimal("45.0"));
            env2.setRecyclablePercentage(new BigDecimal("85.0"));
            env2.setNotes("Ahorra 3500 litros de agua comparado con jeans convencionales");
            product2.setEnvironmentalData(env2);
            env2.setProduct(product2);

            Set<Certification> cert2 = new HashSet<>();
            cert2.add(certifications.get(3)); // Bluesign
            cert2.add(certifications.get(6)); // B Corporation
            product2.setCertifications(cert2);

            Product product3 = new Product();
            product3.setName("Vestido Estampado Sostenible");
            product3.setDescription("Vestido elegante de bambú y algodón orgánico con estampados botánicos");
            product3.setPrice(new BigDecimal("89.99"));
            product3.setImageUrl("https://example.com/vestido-bambu.jpg");
            product3.setStock(75);
            product3.setBrand(brands.get(2));
            product3.setCategory(categories.get(2));
            product3.setIsActive(true);

            EnvironmentalData env3 = new EnvironmentalData();
            env3.setCarbonFootprint(new BigDecimal("5.2"));
            env3.setMaterial("70% bambú, 30% algodón orgánico");
            env3.setCountryOfOrigin("China");
            env3.setEnergyConsumption(new BigDecimal("28.0"));
            env3.setRecyclablePercentage(new BigDecimal("90.0"));
            env3.setNotes("El bambú crece sin pesticidas y absorbe más CO2 que el algodón");
            product3.setEnvironmentalData(env3);
            env3.setProduct(product3);

            Set<Certification> cert3 = new HashSet<>();
            cert3.add(certifications.get(0)); // GOTS
            cert3.add(certifications.get(2)); // Fair Trade
            product3.setCertifications(cert3);

            Product product4 = new Product();
            product4.setName("Chaqueta Impermeable Reciclada");
            product4.setDescription("Chaqueta técnica impermeable hecha de poliéster 100% reciclado de redes de pesca");
            product4.setPrice(new BigDecimal("149.99"));
            product4.setImageUrl("https://example.com/chaqueta-reciclada.jpg");
            product4.setStock(60);
            product4.setBrand(brands.get(3));
            product4.setCategory(categories.get(3));
            product4.setIsActive(true);

            EnvironmentalData env4 = new EnvironmentalData();
            env4.setCarbonFootprint(new BigDecimal("12.8"));
            env4.setMaterial("Poliéster reciclado de redes de pesca");
            env4.setCountryOfOrigin("Portugal");
            env4.setEnergyConsumption(new BigDecimal("55.0"));
            env4.setRecyclablePercentage(new BigDecimal("100.0"));
            env4.setNotes("Ayuda a limpiar los océanos, libre de PFC tóxicos");
            product4.setEnvironmentalData(env4);
            env4.setProduct(product4);

            Set<Certification> cert4 = new HashSet<>();
            cert4.add(certifications.get(3)); // Bluesign
            cert4.add(certifications.get(4)); // Cradle to Cradle
            product4.setCertifications(cert4);

            Product product5 = new Product();
            product5.setName("Zapatillas Veganas Urbanas");
            product5.setDescription("Zapatillas deportivas de cuero vegano y caucho natural, cómodas y elegantes");
            product5.setPrice(new BigDecimal("119.99"));
            product5.setImageUrl("https://example.com/zapatillas-veganas.jpg");
            product5.setStock(90);
            product5.setBrand(brands.get(4));
            product5.setCategory(categories.get(4));
            product5.setIsActive(true);

            EnvironmentalData env5 = new EnvironmentalData();
            env5.setCarbonFootprint(new BigDecimal("7.5"));
            env5.setMaterial("Cuero vegano, caucho natural, algodón orgánico");
            env5.setCountryOfOrigin("Brasil");
            env5.setEnergyConsumption(new BigDecimal("35.0"));
            env5.setRecyclablePercentage(new BigDecimal("80.0"));
            env5.setNotes("Sin productos animales, comercio justo con comunidades locales");
            product5.setEnvironmentalData(env5);
            env5.setProduct(product5);

            Set<Certification> cert5 = new HashSet<>();
            cert5.add(certifications.get(2)); // Fair Trade
            cert5.add(certifications.get(6)); // B Corporation
            product5.setCertifications(cert5);

            Product product6 = new Product();
            product6.setName("Mochila Eco de Lona Reciclada");
            product6.setDescription("Mochila resistente fabricada con lona reciclada y forros de botellas PET");
            product6.setPrice(new BigDecimal("59.99"));
            product6.setImageUrl("https://example.com/mochila-reciclada.jpg");
            product6.setStock(120);
            product6.setBrand(brands.get(5));
            product6.setCategory(categories.get(5));
            product6.setIsActive(true);

            EnvironmentalData env6 = new EnvironmentalData();
            env6.setCarbonFootprint(new BigDecimal("4.2"));
            env6.setMaterial("Lona reciclada, poliéster reciclado");
            env6.setCountryOfOrigin("Vietnam");
            env6.setEnergyConsumption(new BigDecimal("22.0"));
            env6.setRecyclablePercentage(new BigDecimal("95.0"));
            env6.setNotes("Fabricada con el equivalente a 15 botellas de plástico recicladas");
            product6.setEnvironmentalData(env6);
            env6.setProduct(product6);

            Set<Certification> cert6 = new HashSet<>();
            cert6.add(certifications.get(7)); // EU Ecolabel
            product6.setCertifications(cert6);

            Product product7 = new Product();
            product7.setName("Boxer de Algodón Orgánico Pack 3");
            product7.setDescription("Pack de 3 boxers de algodón 100% orgánico, suaves y transpirables");
            product7.setPrice(new BigDecimal("34.99"));
            product7.setImageUrl("https://example.com/boxer-organico.jpg");
            product7.setStock(200);
            product7.setBrand(brands.get(6));
            product7.setCategory(categories.get(6));
            product7.setIsActive(true);

            EnvironmentalData env7 = new EnvironmentalData();
            env7.setCarbonFootprint(new BigDecimal("1.8"));
            env7.setMaterial("Algodón orgánico");
            env7.setCountryOfOrigin("Turquía");
            env7.setEnergyConsumption(new BigDecimal("12.0"));
            env7.setRecyclablePercentage(new BigDecimal("100.0"));
            env7.setNotes("Libre de químicos irritantes, ideal para piel sensible");
            product7.setEnvironmentalData(env7);
            env7.setProduct(product7);

            Set<Certification> cert7 = new HashSet<>();
            cert7.add(certifications.get(0)); // GOTS
            cert7.add(certifications.get(1)); // OEKO-TEX
            product7.setCertifications(cert7);

            Product product8 = new Product();
            product8.setName("Leggings Deportivos Reciclados");
            product8.setDescription("Leggings deportivos de alta compresión hechos de nylon reciclado de redes de pesca");
            product8.setPrice(new BigDecimal("54.99"));
            product8.setImageUrl("https://example.com/leggings-deportivos.jpg");
            product8.setStock(140);
            product8.setBrand(brands.get(7));
            product8.setCategory(categories.get(7));
            product8.setIsActive(true);

            EnvironmentalData env8 = new EnvironmentalData();
            env8.setCarbonFootprint(new BigDecimal("6.0"));
            env8.setMaterial("Nylon reciclado, elastano");
            env8.setCountryOfOrigin("Italia");
            env8.setEnergyConsumption(new BigDecimal("30.0"));
            env8.setRecyclablePercentage(new BigDecimal("85.0"));
            env8.setNotes("Material técnico de alto rendimiento, secado rápido");
            product8.setEnvironmentalData(env8);
            env8.setProduct(product8);

            Set<Certification> cert8 = new HashSet<>();
            cert8.add(certifications.get(3)); // Bluesign
            cert8.add(certifications.get(1)); // OEKO-TEX
            product8.setCertifications(cert8);

            Product product9 = new Product();
            product9.setName("Body de Algodón Orgánico para Bebé");
            product9.setDescription("Body suave de algodón orgánico hipoalergénico para bebés de 0-12 meses");
            product9.setPrice(new BigDecimal("19.99"));
            product9.setImageUrl("https://example.com/body-bebe.jpg");
            product9.setStock(180);
            product9.setBrand(brands.get(8));
            product9.setCategory(categories.get(8));
            product9.setIsActive(true);

            EnvironmentalData env9 = new EnvironmentalData();
            env9.setCarbonFootprint(new BigDecimal("1.2"));
            env9.setMaterial("Algodón orgánico");
            env9.setCountryOfOrigin("India");
            env9.setEnergyConsumption(new BigDecimal("8.0"));
            env9.setRecyclablePercentage(new BigDecimal("100.0"));
            env9.setNotes("Libre de químicos nocivos, certificado para piel de bebé");
            product9.setEnvironmentalData(env9);
            env9.setProduct(product9);

            Set<Certification> cert9 = new HashSet<>();
            cert9.add(certifications.get(0)); // GOTS
            cert9.add(certifications.get(1)); // OEKO-TEX
            product9.setCertifications(cert9);

            Product product10 = new Product();
            product10.setName("Bikini de Plástico Reciclado del Océano");
            product10.setDescription("Bikini de dos piezas fabricado con plásticos recuperados del océano");
            product10.setPrice(new BigDecimal("69.99"));
            product10.setImageUrl("https://example.com/bikini-reciclado.jpg");
            product10.setStock(85);
            product10.setBrand(brands.get(9));
            product10.setCategory(categories.get(9));
            product10.setIsActive(true);

            EnvironmentalData env10 = new EnvironmentalData();
            env10.setCarbonFootprint(new BigDecimal("3.5"));
            env10.setMaterial("Nylon reciclado de plásticos oceánicos");
            env10.setCountryOfOrigin("España");
            env10.setEnergyConsumption(new BigDecimal("18.0"));
            env10.setRecyclablePercentage(new BigDecimal("100.0"));
            env10.setNotes("Ayuda a limpiar los océanos, resistente al cloro y al agua salada");
            product10.setEnvironmentalData(env10);
            env10.setProduct(product10);

            Set<Certification> cert10 = new HashSet<>();
            cert10.add(certifications.get(4)); // Cradle to Cradle
            cert10.add(certifications.get(7)); // EU Ecolabel
            product10.setCertifications(cert10);

            // Guardar todos los productos
            productRepository.saveAll(java.util.Arrays.asList(
                    product1, product2, product3, product4, product5,
                    product6, product7, product8, product9, product10
            ));

            log.info("✅ 10 productos con datos ambientales cargados");
        } else {
            log.info("⏭️ Los productos ya están cargados, omitiendo...");
        }
    }

    /**
     * Carga 20 órdenes completadas de ejemplo
     */
    @Transactional
    private void loadOrders() {
        if (orderRepository.count() == 0) {
            log.info("📦 Cargando 20 órdenes completadas de ejemplo...");

            List<Customer> customers = customerRepository.findAll();
            List<Product> products = productRepository.findAllWithRelations();

            if (customers.isEmpty() || products.isEmpty()) {
                log.warn("⚠️ No se pueden cargar órdenes sin clientes o productos");
                return;
            }

            // Validar que haya suficientes clientes y productos
            if (customers.size() < 11) {
                log.warn("⚠️ Se necesitan al menos 11 usuarios (1 admin + 10 clientes) para cargar las órdenes. Actualmente hay: {}", customers.size());
                return;
            }

            if (products.size() < 10) {
                log.warn("⚠️ Se necesitan al menos 10 productos para cargar las órdenes. Actualmente hay: {}", products.size());
                return;
            }

            List<Order> orders = new ArrayList<>();

            // Orden 1: BRAND_ADMIN o primer cliente - 3 productos
            // Usar get(1) para saltar el BRAND_ADMIN y usar el primer CUSTOMER
            Order order1 = createCompletedOrder(
                    customers.get(1),
                    "ORD-2024-001",
                    45,
                    new ShippingAddress("Calle Mayor", "123", "Madrid", "Madrid", "España", "28001")
            );
            addOrderItem(order1, products.get(0), 2);
            addOrderItem(order1, products.get(1), 1);
            addOrderItem(order1, products.get(2), 1);
            orders.add(order1);

            // Orden 2: Carlos López - 2 productos
            Order order2 = createCompletedOrder(
                    customers.get(2),
                    "ORD-2024-002",
                    38,
                    new ShippingAddress("Avenida Libertad", "45", "Barcelona", "Barcelona", "España", "08001")
            );
            addOrderItem(order2, products.get(3), 1);
            addOrderItem(order2, products.get(4), 2);
            orders.add(order2);

            // Orden 3: María Rodríguez - 4 productos
            Order order3 = createCompletedOrder(
                    customers.get(3),
                    "ORD-2024-003",
                    32,
                    new ShippingAddress("Plaza España", "78", "Valencia", "Valencia", "España", "46001")
            );
            addOrderItem(order3, products.get(5), 1);
            addOrderItem(order3, products.get(6), 3);
            addOrderItem(order3, products.get(7), 1);
            addOrderItem(order3, products.get(8), 2);
            orders.add(order3);

            // Orden 4: Pedro Martínez - 2 productos
            Order order4 = createCompletedOrder(
                    customers.get(4),
                    "ORD-2024-004",
                    28,
                    new ShippingAddress("Calle Sol", "12", "Sevilla", "Sevilla", "España", "41001")
            );
            addOrderItem(order4, products.get(9), 2);
            addOrderItem(order4, products.get(0), 1);
            orders.add(order4);

            // Orden 5: Laura Sánchez - 3 productos
            Order order5 = createCompletedOrder(
                    customers.get(5),
                    "ORD-2024-005",
                    25,
                    new ShippingAddress("Avenida Constitución", "34", "Málaga", "Málaga", "España", "29001")
            );
            addOrderItem(order5, products.get(1), 1);
            addOrderItem(order5, products.get(3), 1);
            addOrderItem(order5, products.get(6), 2);
            orders.add(order5);

            // Orden 6: Javier Fernández - 2 productos
            Order order6 = createCompletedOrder(
                    customers.get(6),
                    "ORD-2024-006",
                    22,
                    new ShippingAddress("Calle Comercio", "56", "Bilbao", "Vizcaya", "España", "48001")
            );
            addOrderItem(order6, products.get(2), 2);
            addOrderItem(order6, products.get(7), 1);
            orders.add(order6);

            // Orden 7: Sofía Gómez - 5 productos
            Order order7 = createCompletedOrder(
                    customers.get(7),
                    "ORD-2024-007",
                    18,
                    new ShippingAddress("Plaza Mayor", "89", "Zaragoza", "Zaragoza", "España", "50001")
            );
            addOrderItem(order7, products.get(4), 1);
            addOrderItem(order7, products.get(5), 1);
            addOrderItem(order7, products.get(8), 2);
            addOrderItem(order7, products.get(9), 1);
            addOrderItem(order7, products.get(0), 1);
            orders.add(order7);

            // Orden 8: Diego Ruiz - 3 productos
            Order order8 = createCompletedOrder(
                    customers.get(8),
                    "ORD-2024-008",
                    15,
                    new ShippingAddress("Calle Victoria", "23", "Granada", "Granada", "España", "18001")
            );
            addOrderItem(order8, products.get(1), 3);
            addOrderItem(order8, products.get(3), 2);
            addOrderItem(order8, products.get(6), 1);
            orders.add(order8);

            // Orden 9: Elena Torres - 3 productos
            Order order9 = createCompletedOrder(
                    customers.get(9),
                    "ORD-2024-009",
                    12,
                    new ShippingAddress("Avenida Principal", "67", "Murcia", "Murcia", "España", "30001")
            );
            addOrderItem(order9, products.get(2), 1);
            addOrderItem(order9, products.get(5), 2);
            addOrderItem(order9, products.get(7), 1);
            orders.add(order9);

            // Orden 10: Jorge Vázquez - 4 productos
            Order order10 = createCompletedOrder(
                    customers.get(10),
                    "ORD-2024-010",
                    8,
                    new ShippingAddress("Calle Real", "90", "Alicante", "Alicante", "España", "03001")
            );
            addOrderItem(order10, products.get(4), 2);
            addOrderItem(order10, products.get(6), 1);
            addOrderItem(order10, products.get(8), 2);
            addOrderItem(order10, products.get(9), 1);
            orders.add(order10);

            // Orden 11: Ana García (segunda orden) - 2 productos
            Order order11 = createCompletedOrder(
                    customers.get(1),
                    "ORD-2024-011",
                    7,
                    new ShippingAddress("Calle Mayor", "123", "Madrid", "Madrid", "España", "28001")
            );
            addOrderItem(order11, products.get(5), 1);
            addOrderItem(order11, products.get(7), 2);
            orders.add(order11);

            // Orden 12: Carlos López (segunda orden) - 3 productos
            Order order12 = createCompletedOrder(
                    customers.get(2),
                    "ORD-2024-012",
                    6,
                    new ShippingAddress("Avenida Libertad", "45", "Barcelona", "Barcelona", "España", "08001")
            );
            addOrderItem(order12, products.get(0), 2);
            addOrderItem(order12, products.get(3), 1);
            addOrderItem(order12, products.get(8), 1);
            orders.add(order12);

            // Orden 13: María Rodríguez (segunda orden) - 3 productos
            Order order13 = createCompletedOrder(
                    customers.get(3),
                    "ORD-2024-013",
                    5,
                    new ShippingAddress("Plaza España", "78", "Valencia", "Valencia", "España", "46001")
            );
            addOrderItem(order13, products.get(2), 1);
            addOrderItem(order13, products.get(4), 2);
            addOrderItem(order13, products.get(9), 1);
            orders.add(order13);

            // Orden 14: Pedro Martínez (segunda orden) - 2 productos
            Order order14 = createCompletedOrder(
                    customers.get(4),
                    "ORD-2024-014",
                    4,
                    new ShippingAddress("Calle Sol", "12", "Sevilla", "Sevilla", "España", "41001")
            );
            addOrderItem(order14, products.get(1), 3);
            addOrderItem(order14, products.get(6), 1);
            orders.add(order14);

            // Orden 15: Laura Sánchez (segunda orden) - 4 productos
            Order order15 = createCompletedOrder(
                    customers.get(5),
                    "ORD-2024-015",
                    3,
                    new ShippingAddress("Avenida Constitución", "34", "Málaga", "Málaga", "España", "29001")
            );
            addOrderItem(order15, products.get(0), 1);
            addOrderItem(order15, products.get(2), 1);
            addOrderItem(order15, products.get(5), 2);
            addOrderItem(order15, products.get(8), 1);
            orders.add(order15);

            // Orden 16: Javier Fernández (segunda orden) - 3 productos
            Order order16 = createCompletedOrder(
                    customers.get(6),
                    "ORD-2024-016",
                    2,
                    new ShippingAddress("Calle Comercio", "56", "Bilbao", "Vizcaya", "España", "48001")
            );
            addOrderItem(order16, products.get(3), 2);
            addOrderItem(order16, products.get(7), 1);
            addOrderItem(order16, products.get(9), 2);
            orders.add(order16);

            // Orden 17: Sofía Gómez (segunda orden) - 2 productos
            Order order17 = createCompletedOrder(
                    customers.get(7),
                    "ORD-2024-017",
                    1,
                    new ShippingAddress("Plaza Mayor", "89", "Zaragoza", "Zaragoza", "España", "50001")
            );
            addOrderItem(order17, products.get(1), 2);
            addOrderItem(order17, products.get(4), 1);
            orders.add(order17);

            // Orden 18: Diego Ruiz (segunda orden) - 4 productos
            Order order18 = createCompletedOrder(
                    customers.get(8),
                    "ORD-2024-018",
                    1,
                    new ShippingAddress("Calle Victoria", "23", "Granada", "Granada", "España", "18001")
            );
            addOrderItem(order18, products.get(0), 1);
            addOrderItem(order18, products.get(5), 1);
            addOrderItem(order18, products.get(6), 2);
            addOrderItem(order18, products.get(9), 1);
            orders.add(order18);

            // Orden 19: Elena Torres (segunda orden) - 3 productos
            Order order19 = createCompletedOrder(
                    customers.get(9),
                    "ORD-2024-019",
                    1,
                    new ShippingAddress("Avenida Principal", "67", "Murcia", "Murcia", "España", "30001")
            );
            addOrderItem(order19, products.get(3), 2);
            addOrderItem(order19, products.get(7), 1);
            addOrderItem(order19, products.get(8), 2);
            orders.add(order19);

            // Orden 20: Jorge Vázquez (segunda orden) - 3 productos
            Order order20 = createCompletedOrder(
                    customers.get(10),
                    "ORD-2024-020",
                    1,
                    new ShippingAddress("Calle Real", "90", "Alicante", "Alicante", "España", "03001")
            );
            addOrderItem(order20, products.get(2), 1);
            addOrderItem(order20, products.get(4), 2);
            addOrderItem(order20, products.get(6), 1);
            orders.add(order20);

            // Calcular totales y actualizar montos de pago
            orders.forEach(order -> {
                order.calculateImpact();
                // Actualizar el monto del pago con el total de la orden
                if (order.getPayment() != null) {
                    order.getPayment().setAmount(order.getTotalAmount());
                }
            });
            orderRepository.saveAll(orders);

            log.info("✅ {} órdenes completadas cargadas", orders.size());
        } else {
            long orderCount = orderRepository.count();
            log.info("⏭️ Ya existen {} órdenes en la base de datos, omitiendo carga automática", orderCount);
        }
    }

    /**
     * Helper: Crea una orden completada
     */
    private Order createCompletedOrder(Customer customer, String orderNumber, int daysAgo, ShippingAddress address) {
        LocalDateTime orderDateTime = LocalDateTime.now().minusDays(daysAgo);

        Payment payment = Payment.builder()
                .amount(BigDecimal.ZERO) // Se calculará después
                .paymentDate(orderDateTime)
                .build();

        Order order = Order.builder()
                .customer(customer)
                .orderNumber(orderNumber)
                .status(OrderStatus.DELIVERED)
                .shippingAddress(address)
                .payment(payment)
                .ecoPointsEarned(30)
                .items(new ArrayList<>())
                .build();

        // Convertir LocalDateTime a Date
        order.setOrderDate(java.util.Date.from(orderDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
        order.setShippingDate(java.util.Date.from(orderDateTime.plusDays(2).atZone(java.time.ZoneId.systemDefault()).toInstant()));
        order.setDeliveryDate(java.util.Date.from(orderDateTime.plusDays(5).atZone(java.time.ZoneId.systemDefault()).toInstant()));

        return order;
    }

    /**
     * Helper: Agrega un item a una orden
     */
    private void addOrderItem(Order order, Product product, int quantity) {
        OrderItem item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .build();

        // Establecer datos ambientales si existen
        try {
            if (product.getEnvironmentalData() != null) {
                BigDecimal carbonFootprint = product.getEnvironmentalData().getCarbonFootprint();
                if (carbonFootprint != null) {
                    item.setItemCarbonFootprint(carbonFootprint);
                    item.setCO2Saved(carbonFootprint.multiply(new BigDecimal("0.30")));
                }
            }
        } catch (Exception e) {
            // Si hay error al cargar datos ambientales, usar valores por defecto
            log.warn("No se pudieron cargar datos ambientales para producto {}: {}", product.getId(), e.getMessage());
            item.setItemCarbonFootprint(BigDecimal.ZERO);
            item.setCO2Saved(BigDecimal.ZERO);
        }

        item.calculateSubtotal();
        order.getItems().add(item);
    }
}
