package com.dp.dpshopbackend;

import com.dp.dpshopbackend.repository.*;
import com.dp.dpshopbackend.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class DpshopBackendApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DpshopBackendApplication.class);

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ScategoryRepository scategoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private AddressLivraisonRepository addressLivraisonRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(DpshopBackendApplication.class, args);
        createDirectoryIfItDoesntExist();
    }

    private static void createDirectoryIfItDoesntExist() {
        Path path = Paths.get(System.getProperty("user.home") + "/shopmania/productphotos/");

        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ie) {
                LOG.error(String.format("Problem creating directory %s", path));
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {

        /*
        Category c1 = categoryRepository.save(new Category(1L, "cat1", "cat1"));
        Category c2 = categoryRepository.save(new Category(2L, "cat2", "cat2"));
        Category c3 = categoryRepository.save(new Category(3L, "cat3", "cat3"));

        Scategory sc1 = scategoryRepository.save(new Scategory(1L, "scat1", "Fashion & Beauty", c1));
        Scategory sc2 = scategoryRepository.save(new Scategory(2L, "scat2", "Kids & Babies Clothes", c2));
        Scategory sc3 = scategoryRepository.save(new Scategory(3L, "scat3", "Men & Women Clothes", c3));
        Scategory sc4 = scategoryRepository.save(new Scategory(4L, "scat4", "Gadgets & Accessories", c2));
        Scategory sc5 = scategoryRepository.save(new Scategory(5L, "scat5", "Electronics & Accessories", c3));

        Article p1 = articleRepository.save(new Article(1L, "prod1", "HP-ProBooks", 150, 1700.0, 1800.0, true, true, "prod1", "product-1.jpg", sc1));
        Article p2 = articleRepository.save(new Article(2L, "prod2", "Mac-Pro", 150, 1900.0, 1800.0, true, true, "prod1", "product-2.jpg", sc3));
        Article p3 = articleRepository.save(new Article(3L, "prod3", "Robe-Mariage", 150, 2000.0, 1800.0, false, true, "prod1", "product-3.jpg", sc2));
        Article p4 = articleRepository.save(new Article(4L, "prod4", "Jupe-Nuit", 150, 3000.0, 1800.0, true, true, "prod1", "product-4.jpg", sc1));
        Article p5 = articleRepository.save(new Article(5L, "prod5", "HP EliteBook", 150, 1700.0, 1800.0, false, true, "prod1", "product-5.jpg", sc4));
        Article p6 = articleRepository.save(new Article(6L, "prod6", "Mac-OS", 150, 1900.0, 1800.0, true, true, "prod1", "product-6.jpg", sc1));
        Article p7 = articleRepository.save(new Article(7L, "prod7", "Pantallon", 150, 2000.0, 1800.0, true, true, "prod1", "product-7.jpg", sc1));
        Article p8 = articleRepository.save(new Article(8L, "prod8", "Cullote", 150, 3000.0, 1800.0, false, true, "prod1", "product-8.jpg", sc3));
        Article p9 = articleRepository.save(new Article(9L, "prod9", "Ensembes", 150, 5000.0, 1800.0, true, true, "prod1", "product-9.jpg", sc3));
        Article p10 = articleRepository.save(new Article(10L, "prod10", "Pantallon", 150, 4000.0, 1800.0, false, true, "prod1", "product-10.jpg", sc4));
        Article p11 = articleRepository.save(new Article(11L, "prod11", "Robe Ete", 150, 50000.0, 1800.0, true, true, "prod1", "photo1.jpg", sc1));
        Article p12 = articleRepository.save(new Article(12L, "prod12", "HuperCool", 150, 20000.0, 1800.0, true, true, "prod1", "photo2.jpg", sc3));
        Article p13 = articleRepository.save(new Article(13L, "prod13", "Parfums", 150, 1600.0, 1800.0, false, true, "prod1", "photo3.jpg", sc2));
        Article p14 = articleRepository.save(new Article(14L, "prod14", "Eaux de Cologne", 150, 1500.0, 1800.0, true, false, "prod1", "photo4.jpg", sc1));
        Article p15 = articleRepository.save(new Article(15L, "prod15", "Ordinateurs", 150, 1700.0, 1800.0, false, false, "prod1", "photo5.jpg", sc4));
        Article p16 = articleRepository.save(new Article(16L, "prod16", "Mini PC", 150, 20000.0, 1800.0, true, true, "prod1", "photo6.jpg", sc1));
        Article p17 = articleRepository.save(new Article(17L, "prod17", "product-17", 150, 14000.0, 1800.0, true, false, "prod1", "photo7.jpg", sc1));
        Article p18 = articleRepository.save(new Article(18L, "prod18", "product-18", 150, 12000.0, 1800.0, false, false, "prod1", "photo8.jpg", sc3));
        Article p19 = articleRepository.save(new Article(19L, "prod19", "product-19", 150, 10000.0, 1800.0, true, false, "prod1", "photo9.jpg", sc3));
        Article p20 = articleRepository.save(new Article(20L, "prod20", "product-20", 150, 12000.0, 1800.0, false, false, "prod1", "photo10.jpg", sc4));

        Fournisseur f1 = fournisseurRepository.save(new Fournisseur(1L, "f1", "f1", "f1", "f1", "masterou@gmail.fr", "f1", "f1", "f1", p1));
        Fournisseur f2 = fournisseurRepository.save(new Fournisseur(2L, "f2", "f2", "f2", "f2", "thirtsoft@gmail.com", "f2", "f2", "f2", p2));
        Fournisseur f3 = fournisseurRepository.save(new Fournisseur(3L, "f3", "f3", "f3", "f3", "m.diallo233@unig-zig.sn", "f3", "f3", "f3", p2));
        Fournisseur f4 = fournisseurRepository.save(new Fournisseur(4L, "f4", "f4", "f4", "f4", "thirtsoft@gmail.com", "f4", "f4", "f4", p1));

        Country count1 = countryRepository.save(new Country(1L, "SEN", "SENEGAL"));
        Country count2 = countryRepository.save(new Country(2L, "USA", "Etats-Unies"));
        Country count3 = countryRepository.save(new Country(3L, "GUIN", "Guinnée-Conakry"));
        Country count4 = countryRepository.save(new Country(4L, "JAP", "Japon"));
        Country count5 = countryRepository.save(new Country(5L, "GB", "Gambie"));
        Country count6 = countryRepository.save(new Country(6L, "CH", "Chine"));
        Country count7 = countryRepository.save(new Country(7L, "AR", "Arabie-Souadite"));
        Country count8 = countryRepository.save(new Country(8L, "FR", "France"));
        Country count9 = countryRepository.save(new Country(9L, "NG", "Nigeria"));
        Country count10 = countryRepository.save(new Country(10L, "Ind", "Inde"));

        State state1 = stateRepository.save(new State(1L, "Dakar", count1));
        State state2 = stateRepository.save(new State(2L, "Ziguinchor", count1));
        State state3 = stateRepository.save(new State(3L, "Thies", count1));
        State state4 = stateRepository.save(new State(4L, "Fatick", count1));
        State state5 = stateRepository.save(new State(5L, "California", count2));
        State state6 = stateRepository.save(new State(6L, "Japon", count4));
        State state7 = stateRepository.save(new State(7L, "Labe", count3));
        State state8 = stateRepository.save(new State(8L, "Chine", count6));
        State state9 = stateRepository.save(new State(9L, "Dalaba", count3));
        State state10 = stateRepository.save(new State(10L, "Accra", count9));


        Role useRole = new Role(RoleName.ROLE_USER);
        Role assistantRole = new Role(RoleName.ROLE_ASSISTANT);
        Role managerRole = new Role(RoleName.ROLE_MANAGER);
        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        roleRepository.save(useRole);
        roleRepository.save(assistantRole);
        roleRepository.save(managerRole);
        roleRepository.save(adminRole);

        Utilisateur user = new Utilisateur();
        user.setId(1L);
        user.setUsername("User");
        user.setName("User");
        user.setPassword(bCryptPasswordEncoder.encode("user1234"));
        Utilisateur manager = new Utilisateur();
        manager.setId(2L);
        manager.setUsername("Manager");
        manager.setName("Manager");
        manager.setPassword("manager1234");
        Utilisateur admin = new Utilisateur();
        admin.setId(3L);
        admin.setUsername("Admin");
        admin.setName("Admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin1234"));
        utilisateurRepository.save(user);
        utilisateurRepository.save(manager);
        utilisateurRepository.save(admin);

        utilisateurService.addRoleToUser("Admin", RoleName.ROLE_ADMIN);

        */
        //    utilisateurService.addRoleToUser("User", RoleName.ROLE_USER);

    }
}
