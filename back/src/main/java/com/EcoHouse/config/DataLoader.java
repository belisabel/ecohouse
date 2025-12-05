package com.EcoHouse.config;

import com.EcoHouse.category.model.Category;
import com.EcoHouse.category.repository.CategoryRepository;
import com.EcoHouse.product.model.Brand;
import com.EcoHouse.product.model.Certification;
import com.EcoHouse.product.repository.BrandRepository;
import com.EcoHouse.product.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) {
        loadBrands();
        loadCategories();
        loadCertifications();
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
}
