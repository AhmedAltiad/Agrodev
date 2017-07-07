package com.agrobourse.dev.web.rest;

import com.agrobourse.dev.AgroBourse360SiApp;

import com.agrobourse.dev.domain.GPO;
import com.agrobourse.dev.repository.GPORepository;
import com.agrobourse.dev.repository.search.GPOSearchRepository;
import com.agrobourse.dev.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GPOResource REST controller.
 *
 * @see GPOResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgroBourse360SiApp.class)
public class GPOResourceIntTest {

    private static final String DEFAULT_SOCIETEPOSTALE = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETEPOSTALE = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIETELEGALE = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETELEGALE = "BBBBBBBBBB";

    private static final String DEFAULT_QUALITE = "AAAAAAAAAA";
    private static final String UPDATED_QUALITE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADDADRESSE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDADRESSE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDADRESSE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDADRESSE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_BOITEPOSTALE = "AAAAAAAAAA";
    private static final String UPDATED_BOITEPOSTALE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ADDCODEPOSTAL = 1;
    private static final Integer UPDATED_ADDCODEPOSTAL = 2;

    private static final String DEFAULT_ADDVILLE = "AAAAAAAAAA";
    private static final String UPDATED_ADDVILLE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONEUNIQUE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONEUNIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_NUM_INTERNATIONAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_NUM_INTERNATIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGERIE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGERIE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGERIEUNIQUE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGERIEUNIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMDEPARTEMENT = 1;
    private static final Integer UPDATED_NUMDEPARTEMENT = 2;

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NAF_IMPORT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NAF_IMPORT = "BBBBBBBBBB";

    private static final String DEFAULT_SIVET = "AAAAAAAAAA";
    private static final String UPDATED_SIVET = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    private static final Integer DEFAULT_POPULATIONTOTALE = 1;
    private static final Integer UPDATED_POPULATIONTOTALE = 2;

    private static final String DEFAULT_RESEAU = "AAAAAAAAAA";
    private static final String UPDATED_RESEAU = "BBBBBBBBBB";

    private static final String DEFAULT_ENSEIGNE = "AAAAAAAAAA";
    private static final String UPDATED_ENSEIGNE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT_JUDICIAIRE = "AAAAAAAAAA";
    private static final String UPDATED_STATUT_JUDICIAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_CA = "AAAAAAAAAA";
    private static final String UPDATED_CA = "BBBBBBBBBB";

    private static final String DEFAULT_CAPITAL_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_CAPITAL_SOCIAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION_ENTREPRISE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION_ENTREPRISE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EFFECTIFS = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIFS = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_FAXFAXINGINTERNATIONAL = "AAAAAAAAAA";
    private static final String UPDATED_FAXFAXINGINTERNATIONAL = "BBBBBBBBBB";

    @Autowired
    private GPORepository gPORepository;

    @Autowired
    private GPOSearchRepository gPOSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGPOMockMvc;

    private GPO gPO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GPOResource gPOResource = new GPOResource(gPORepository, gPOSearchRepository);
        this.restGPOMockMvc = MockMvcBuilders.standaloneSetup(gPOResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GPO createEntity(EntityManager em) {
        GPO gPO = new GPO()
            .societepostale(DEFAULT_SOCIETEPOSTALE)
            .societelegale(DEFAULT_SOCIETELEGALE)
            .qualite(DEFAULT_QUALITE)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .addadresse1(DEFAULT_ADDADRESSE_1)
            .addadresse2(DEFAULT_ADDADRESSE_2)
            .boitepostale(DEFAULT_BOITEPOSTALE)
            .addcodepostal(DEFAULT_ADDCODEPOSTAL)
            .addville(DEFAULT_ADDVILLE)
            .telephoneunique(DEFAULT_TELEPHONEUNIQUE)
            .telephone(DEFAULT_TELEPHONE)
            .telephoneNumInternational(DEFAULT_TELEPHONE_NUM_INTERNATIONAL)
            .messagerie(DEFAULT_MESSAGERIE)
            .messagerieunique(DEFAULT_MESSAGERIEUNIQUE)
            .region(DEFAULT_REGION)
            .departement(DEFAULT_DEPARTEMENT)
            .numdepartement(DEFAULT_NUMDEPARTEMENT)
            .categorie(DEFAULT_CATEGORIE)
            .codeNafImport(DEFAULT_CODE_NAF_IMPORT)
            .sivet(DEFAULT_SIVET)
            .web(DEFAULT_WEB)
            .populationtotale(DEFAULT_POPULATIONTOTALE)
            .reseau(DEFAULT_RESEAU)
            .enseigne(DEFAULT_ENSEIGNE)
            .statutEtablissement(DEFAULT_STATUT_ETABLISSEMENT)
            .statutJudiciaire(DEFAULT_STATUT_JUDICIAIRE)
            .ca(DEFAULT_CA)
            .capitalSocial(DEFAULT_CAPITAL_SOCIAL)
            .dateCreationEntreprise(DEFAULT_DATE_CREATION_ENTREPRISE)
            .effectifs(DEFAULT_EFFECTIFS)
            .fax(DEFAULT_FAX)
            .faxfaxinginternational(DEFAULT_FAXFAXINGINTERNATIONAL);
        return gPO;
    }

    @Before
    public void initTest() {
        gPOSearchRepository.deleteAll();
        gPO = createEntity(em);
    }

    @Test
    @Transactional
    public void createGPO() throws Exception {
        int databaseSizeBeforeCreate = gPORepository.findAll().size();

        // Create the GPO
        restGPOMockMvc.perform(post("/api/g-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPO)))
            .andExpect(status().isCreated());

        // Validate the GPO in the database
        List<GPO> gPOList = gPORepository.findAll();
        assertThat(gPOList).hasSize(databaseSizeBeforeCreate + 1);
        GPO testGPO = gPOList.get(gPOList.size() - 1);
        assertThat(testGPO.getSocietepostale()).isEqualTo(DEFAULT_SOCIETEPOSTALE);
        assertThat(testGPO.getSocietelegale()).isEqualTo(DEFAULT_SOCIETELEGALE);
        assertThat(testGPO.getQualite()).isEqualTo(DEFAULT_QUALITE);
        assertThat(testGPO.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testGPO.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testGPO.getAddadresse1()).isEqualTo(DEFAULT_ADDADRESSE_1);
        assertThat(testGPO.getAddadresse2()).isEqualTo(DEFAULT_ADDADRESSE_2);
        assertThat(testGPO.getBoitepostale()).isEqualTo(DEFAULT_BOITEPOSTALE);
        assertThat(testGPO.getAddcodepostal()).isEqualTo(DEFAULT_ADDCODEPOSTAL);
        assertThat(testGPO.getAddville()).isEqualTo(DEFAULT_ADDVILLE);
        assertThat(testGPO.getTelephoneunique()).isEqualTo(DEFAULT_TELEPHONEUNIQUE);
        assertThat(testGPO.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testGPO.getTelephoneNumInternational()).isEqualTo(DEFAULT_TELEPHONE_NUM_INTERNATIONAL);
        assertThat(testGPO.getMessagerie()).isEqualTo(DEFAULT_MESSAGERIE);
        assertThat(testGPO.getMessagerieunique()).isEqualTo(DEFAULT_MESSAGERIEUNIQUE);
        assertThat(testGPO.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testGPO.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testGPO.getNumdepartement()).isEqualTo(DEFAULT_NUMDEPARTEMENT);
        assertThat(testGPO.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testGPO.getCodeNafImport()).isEqualTo(DEFAULT_CODE_NAF_IMPORT);
        assertThat(testGPO.getSivet()).isEqualTo(DEFAULT_SIVET);
        assertThat(testGPO.getWeb()).isEqualTo(DEFAULT_WEB);
        assertThat(testGPO.getPopulationtotale()).isEqualTo(DEFAULT_POPULATIONTOTALE);
        assertThat(testGPO.getReseau()).isEqualTo(DEFAULT_RESEAU);
        assertThat(testGPO.getEnseigne()).isEqualTo(DEFAULT_ENSEIGNE);
        assertThat(testGPO.getStatutEtablissement()).isEqualTo(DEFAULT_STATUT_ETABLISSEMENT);
        assertThat(testGPO.getStatutJudiciaire()).isEqualTo(DEFAULT_STATUT_JUDICIAIRE);
        assertThat(testGPO.getCa()).isEqualTo(DEFAULT_CA);
        assertThat(testGPO.getCapitalSocial()).isEqualTo(DEFAULT_CAPITAL_SOCIAL);
        assertThat(testGPO.getDateCreationEntreprise()).isEqualTo(DEFAULT_DATE_CREATION_ENTREPRISE);
        assertThat(testGPO.getEffectifs()).isEqualTo(DEFAULT_EFFECTIFS);
        assertThat(testGPO.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testGPO.getFaxfaxinginternational()).isEqualTo(DEFAULT_FAXFAXINGINTERNATIONAL);

        // Validate the GPO in Elasticsearch
        GPO gPOEs = gPOSearchRepository.findOne(testGPO.getId());
        assertThat(gPOEs).isEqualToComparingFieldByField(testGPO);
    }

    @Test
    @Transactional
    public void createGPOWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gPORepository.findAll().size();

        // Create the GPO with an existing ID
        gPO.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGPOMockMvc.perform(post("/api/g-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GPO> gPOList = gPORepository.findAll();
        assertThat(gPOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGPOS() throws Exception {
        // Initialize the database
        gPORepository.saveAndFlush(gPO);

        // Get all the gPOList
        restGPOMockMvc.perform(get("/api/g-pos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gPO.getId().intValue())))
            .andExpect(jsonPath("$.[*].societepostale").value(hasItem(DEFAULT_SOCIETEPOSTALE.toString())))
            .andExpect(jsonPath("$.[*].societelegale").value(hasItem(DEFAULT_SOCIETELEGALE.toString())))
            .andExpect(jsonPath("$.[*].qualite").value(hasItem(DEFAULT_QUALITE.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].addadresse1").value(hasItem(DEFAULT_ADDADRESSE_1.toString())))
            .andExpect(jsonPath("$.[*].addadresse2").value(hasItem(DEFAULT_ADDADRESSE_2.toString())))
            .andExpect(jsonPath("$.[*].boitepostale").value(hasItem(DEFAULT_BOITEPOSTALE.toString())))
            .andExpect(jsonPath("$.[*].addcodepostal").value(hasItem(DEFAULT_ADDCODEPOSTAL)))
            .andExpect(jsonPath("$.[*].addville").value(hasItem(DEFAULT_ADDVILLE.toString())))
            .andExpect(jsonPath("$.[*].telephoneunique").value(hasItem(DEFAULT_TELEPHONEUNIQUE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].telephoneNumInternational").value(hasItem(DEFAULT_TELEPHONE_NUM_INTERNATIONAL.toString())))
            .andExpect(jsonPath("$.[*].messagerie").value(hasItem(DEFAULT_MESSAGERIE.toString())))
            .andExpect(jsonPath("$.[*].messagerieunique").value(hasItem(DEFAULT_MESSAGERIEUNIQUE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].numdepartement").value(hasItem(DEFAULT_NUMDEPARTEMENT)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].codeNafImport").value(hasItem(DEFAULT_CODE_NAF_IMPORT.toString())))
            .andExpect(jsonPath("$.[*].sivet").value(hasItem(DEFAULT_SIVET.toString())))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
            .andExpect(jsonPath("$.[*].populationtotale").value(hasItem(DEFAULT_POPULATIONTOTALE)))
            .andExpect(jsonPath("$.[*].reseau").value(hasItem(DEFAULT_RESEAU.toString())))
            .andExpect(jsonPath("$.[*].enseigne").value(hasItem(DEFAULT_ENSEIGNE.toString())))
            .andExpect(jsonPath("$.[*].statutEtablissement").value(hasItem(DEFAULT_STATUT_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].statutJudiciaire").value(hasItem(DEFAULT_STATUT_JUDICIAIRE.toString())))
            .andExpect(jsonPath("$.[*].ca").value(hasItem(DEFAULT_CA.toString())))
            .andExpect(jsonPath("$.[*].capitalSocial").value(hasItem(DEFAULT_CAPITAL_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].dateCreationEntreprise").value(hasItem(DEFAULT_DATE_CREATION_ENTREPRISE.toString())))
            .andExpect(jsonPath("$.[*].effectifs").value(hasItem(DEFAULT_EFFECTIFS.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].faxfaxinginternational").value(hasItem(DEFAULT_FAXFAXINGINTERNATIONAL.toString())));
    }

    @Test
    @Transactional
    public void getGPO() throws Exception {
        // Initialize the database
        gPORepository.saveAndFlush(gPO);

        // Get the gPO
        restGPOMockMvc.perform(get("/api/g-pos/{id}", gPO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gPO.getId().intValue()))
            .andExpect(jsonPath("$.societepostale").value(DEFAULT_SOCIETEPOSTALE.toString()))
            .andExpect(jsonPath("$.societelegale").value(DEFAULT_SOCIETELEGALE.toString()))
            .andExpect(jsonPath("$.qualite").value(DEFAULT_QUALITE.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.addadresse1").value(DEFAULT_ADDADRESSE_1.toString()))
            .andExpect(jsonPath("$.addadresse2").value(DEFAULT_ADDADRESSE_2.toString()))
            .andExpect(jsonPath("$.boitepostale").value(DEFAULT_BOITEPOSTALE.toString()))
            .andExpect(jsonPath("$.addcodepostal").value(DEFAULT_ADDCODEPOSTAL))
            .andExpect(jsonPath("$.addville").value(DEFAULT_ADDVILLE.toString()))
            .andExpect(jsonPath("$.telephoneunique").value(DEFAULT_TELEPHONEUNIQUE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.telephoneNumInternational").value(DEFAULT_TELEPHONE_NUM_INTERNATIONAL.toString()))
            .andExpect(jsonPath("$.messagerie").value(DEFAULT_MESSAGERIE.toString()))
            .andExpect(jsonPath("$.messagerieunique").value(DEFAULT_MESSAGERIEUNIQUE.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT.toString()))
            .andExpect(jsonPath("$.numdepartement").value(DEFAULT_NUMDEPARTEMENT))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.codeNafImport").value(DEFAULT_CODE_NAF_IMPORT.toString()))
            .andExpect(jsonPath("$.sivet").value(DEFAULT_SIVET.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()))
            .andExpect(jsonPath("$.populationtotale").value(DEFAULT_POPULATIONTOTALE))
            .andExpect(jsonPath("$.reseau").value(DEFAULT_RESEAU.toString()))
            .andExpect(jsonPath("$.enseigne").value(DEFAULT_ENSEIGNE.toString()))
            .andExpect(jsonPath("$.statutEtablissement").value(DEFAULT_STATUT_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.statutJudiciaire").value(DEFAULT_STATUT_JUDICIAIRE.toString()))
            .andExpect(jsonPath("$.ca").value(DEFAULT_CA.toString()))
            .andExpect(jsonPath("$.capitalSocial").value(DEFAULT_CAPITAL_SOCIAL.toString()))
            .andExpect(jsonPath("$.dateCreationEntreprise").value(DEFAULT_DATE_CREATION_ENTREPRISE.toString()))
            .andExpect(jsonPath("$.effectifs").value(DEFAULT_EFFECTIFS.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.faxfaxinginternational").value(DEFAULT_FAXFAXINGINTERNATIONAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGPO() throws Exception {
        // Get the gPO
        restGPOMockMvc.perform(get("/api/g-pos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGPO() throws Exception {
        // Initialize the database
        gPORepository.saveAndFlush(gPO);
        gPOSearchRepository.save(gPO);
        int databaseSizeBeforeUpdate = gPORepository.findAll().size();

        // Update the gPO
        GPO updatedGPO = gPORepository.findOne(gPO.getId());
        updatedGPO
            .societepostale(UPDATED_SOCIETEPOSTALE)
            .societelegale(UPDATED_SOCIETELEGALE)
            .qualite(UPDATED_QUALITE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .addadresse1(UPDATED_ADDADRESSE_1)
            .addadresse2(UPDATED_ADDADRESSE_2)
            .boitepostale(UPDATED_BOITEPOSTALE)
            .addcodepostal(UPDATED_ADDCODEPOSTAL)
            .addville(UPDATED_ADDVILLE)
            .telephoneunique(UPDATED_TELEPHONEUNIQUE)
            .telephone(UPDATED_TELEPHONE)
            .telephoneNumInternational(UPDATED_TELEPHONE_NUM_INTERNATIONAL)
            .messagerie(UPDATED_MESSAGERIE)
            .messagerieunique(UPDATED_MESSAGERIEUNIQUE)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .numdepartement(UPDATED_NUMDEPARTEMENT)
            .categorie(UPDATED_CATEGORIE)
            .codeNafImport(UPDATED_CODE_NAF_IMPORT)
            .sivet(UPDATED_SIVET)
            .web(UPDATED_WEB)
            .populationtotale(UPDATED_POPULATIONTOTALE)
            .reseau(UPDATED_RESEAU)
            .enseigne(UPDATED_ENSEIGNE)
            .statutEtablissement(UPDATED_STATUT_ETABLISSEMENT)
            .statutJudiciaire(UPDATED_STATUT_JUDICIAIRE)
            .ca(UPDATED_CA)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .dateCreationEntreprise(UPDATED_DATE_CREATION_ENTREPRISE)
            .effectifs(UPDATED_EFFECTIFS)
            .fax(UPDATED_FAX)
            .faxfaxinginternational(UPDATED_FAXFAXINGINTERNATIONAL);

        restGPOMockMvc.perform(put("/api/g-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGPO)))
            .andExpect(status().isOk());

        // Validate the GPO in the database
        List<GPO> gPOList = gPORepository.findAll();
        assertThat(gPOList).hasSize(databaseSizeBeforeUpdate);
        GPO testGPO = gPOList.get(gPOList.size() - 1);
        assertThat(testGPO.getSocietepostale()).isEqualTo(UPDATED_SOCIETEPOSTALE);
        assertThat(testGPO.getSocietelegale()).isEqualTo(UPDATED_SOCIETELEGALE);
        assertThat(testGPO.getQualite()).isEqualTo(UPDATED_QUALITE);
        assertThat(testGPO.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testGPO.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testGPO.getAddadresse1()).isEqualTo(UPDATED_ADDADRESSE_1);
        assertThat(testGPO.getAddadresse2()).isEqualTo(UPDATED_ADDADRESSE_2);
        assertThat(testGPO.getBoitepostale()).isEqualTo(UPDATED_BOITEPOSTALE);
        assertThat(testGPO.getAddcodepostal()).isEqualTo(UPDATED_ADDCODEPOSTAL);
        assertThat(testGPO.getAddville()).isEqualTo(UPDATED_ADDVILLE);
        assertThat(testGPO.getTelephoneunique()).isEqualTo(UPDATED_TELEPHONEUNIQUE);
        assertThat(testGPO.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testGPO.getTelephoneNumInternational()).isEqualTo(UPDATED_TELEPHONE_NUM_INTERNATIONAL);
        assertThat(testGPO.getMessagerie()).isEqualTo(UPDATED_MESSAGERIE);
        assertThat(testGPO.getMessagerieunique()).isEqualTo(UPDATED_MESSAGERIEUNIQUE);
        assertThat(testGPO.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testGPO.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testGPO.getNumdepartement()).isEqualTo(UPDATED_NUMDEPARTEMENT);
        assertThat(testGPO.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testGPO.getCodeNafImport()).isEqualTo(UPDATED_CODE_NAF_IMPORT);
        assertThat(testGPO.getSivet()).isEqualTo(UPDATED_SIVET);
        assertThat(testGPO.getWeb()).isEqualTo(UPDATED_WEB);
        assertThat(testGPO.getPopulationtotale()).isEqualTo(UPDATED_POPULATIONTOTALE);
        assertThat(testGPO.getReseau()).isEqualTo(UPDATED_RESEAU);
        assertThat(testGPO.getEnseigne()).isEqualTo(UPDATED_ENSEIGNE);
        assertThat(testGPO.getStatutEtablissement()).isEqualTo(UPDATED_STATUT_ETABLISSEMENT);
        assertThat(testGPO.getStatutJudiciaire()).isEqualTo(UPDATED_STATUT_JUDICIAIRE);
        assertThat(testGPO.getCa()).isEqualTo(UPDATED_CA);
        assertThat(testGPO.getCapitalSocial()).isEqualTo(UPDATED_CAPITAL_SOCIAL);
        assertThat(testGPO.getDateCreationEntreprise()).isEqualTo(UPDATED_DATE_CREATION_ENTREPRISE);
        assertThat(testGPO.getEffectifs()).isEqualTo(UPDATED_EFFECTIFS);
        assertThat(testGPO.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testGPO.getFaxfaxinginternational()).isEqualTo(UPDATED_FAXFAXINGINTERNATIONAL);

        // Validate the GPO in Elasticsearch
        GPO gPOEs = gPOSearchRepository.findOne(testGPO.getId());
        assertThat(gPOEs).isEqualToComparingFieldByField(testGPO);
    }

    @Test
    @Transactional
    public void updateNonExistingGPO() throws Exception {
        int databaseSizeBeforeUpdate = gPORepository.findAll().size();

        // Create the GPO

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGPOMockMvc.perform(put("/api/g-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gPO)))
            .andExpect(status().isCreated());

        // Validate the GPO in the database
        List<GPO> gPOList = gPORepository.findAll();
        assertThat(gPOList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGPO() throws Exception {
        // Initialize the database
        gPORepository.saveAndFlush(gPO);
        gPOSearchRepository.save(gPO);
        int databaseSizeBeforeDelete = gPORepository.findAll().size();

        // Get the gPO
        restGPOMockMvc.perform(delete("/api/g-pos/{id}", gPO.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gPOExistsInEs = gPOSearchRepository.exists(gPO.getId());
        assertThat(gPOExistsInEs).isFalse();

        // Validate the database is empty
        List<GPO> gPOList = gPORepository.findAll();
        assertThat(gPOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGPO() throws Exception {
        // Initialize the database
        gPORepository.saveAndFlush(gPO);
        gPOSearchRepository.save(gPO);

        // Search the gPO
        restGPOMockMvc.perform(get("/api/_search/g-pos?query=id:" + gPO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gPO.getId().intValue())))
            .andExpect(jsonPath("$.[*].societepostale").value(hasItem(DEFAULT_SOCIETEPOSTALE.toString())))
            .andExpect(jsonPath("$.[*].societelegale").value(hasItem(DEFAULT_SOCIETELEGALE.toString())))
            .andExpect(jsonPath("$.[*].qualite").value(hasItem(DEFAULT_QUALITE.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].addadresse1").value(hasItem(DEFAULT_ADDADRESSE_1.toString())))
            .andExpect(jsonPath("$.[*].addadresse2").value(hasItem(DEFAULT_ADDADRESSE_2.toString())))
            .andExpect(jsonPath("$.[*].boitepostale").value(hasItem(DEFAULT_BOITEPOSTALE.toString())))
            .andExpect(jsonPath("$.[*].addcodepostal").value(hasItem(DEFAULT_ADDCODEPOSTAL)))
            .andExpect(jsonPath("$.[*].addville").value(hasItem(DEFAULT_ADDVILLE.toString())))
            .andExpect(jsonPath("$.[*].telephoneunique").value(hasItem(DEFAULT_TELEPHONEUNIQUE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].telephoneNumInternational").value(hasItem(DEFAULT_TELEPHONE_NUM_INTERNATIONAL.toString())))
            .andExpect(jsonPath("$.[*].messagerie").value(hasItem(DEFAULT_MESSAGERIE.toString())))
            .andExpect(jsonPath("$.[*].messagerieunique").value(hasItem(DEFAULT_MESSAGERIEUNIQUE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].numdepartement").value(hasItem(DEFAULT_NUMDEPARTEMENT)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].codeNafImport").value(hasItem(DEFAULT_CODE_NAF_IMPORT.toString())))
            .andExpect(jsonPath("$.[*].sivet").value(hasItem(DEFAULT_SIVET.toString())))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
            .andExpect(jsonPath("$.[*].populationtotale").value(hasItem(DEFAULT_POPULATIONTOTALE)))
            .andExpect(jsonPath("$.[*].reseau").value(hasItem(DEFAULT_RESEAU.toString())))
            .andExpect(jsonPath("$.[*].enseigne").value(hasItem(DEFAULT_ENSEIGNE.toString())))
            .andExpect(jsonPath("$.[*].statutEtablissement").value(hasItem(DEFAULT_STATUT_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].statutJudiciaire").value(hasItem(DEFAULT_STATUT_JUDICIAIRE.toString())))
            .andExpect(jsonPath("$.[*].ca").value(hasItem(DEFAULT_CA.toString())))
            .andExpect(jsonPath("$.[*].capitalSocial").value(hasItem(DEFAULT_CAPITAL_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].dateCreationEntreprise").value(hasItem(DEFAULT_DATE_CREATION_ENTREPRISE.toString())))
            .andExpect(jsonPath("$.[*].effectifs").value(hasItem(DEFAULT_EFFECTIFS.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].faxfaxinginternational").value(hasItem(DEFAULT_FAXFAXINGINTERNATIONAL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPO.class);
    }
}
