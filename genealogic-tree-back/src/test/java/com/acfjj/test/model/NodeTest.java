package com.acfjj.test.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.User;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.test.CustomTestWatcher;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class NodeTest {
    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    static public List<Object> testData() {
        User user1 = new User("User1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", "email1", "password1", "Security1", "Phone1", "Nationality1", "Address1", 12345, "Base64Image1");
        User user2 = new User("User2", "FirstName2", 1, LocalDate.of(2001, 2, 2), "Country2", "City2", "email2", "password2", "Security2", "Phone2", "Nationality2", "Address2", 54321, "Base64Image2");

        Node parent1 = new Node(null,"Parent1", "ParentFirstName1", 1, LocalDate.of(1990, 3, 3), "Country3", "City3", user1, 1, "Nationality3", "Address3", 67890, "Base64Image3");
        Node parent2 = new Node(null,"Parent2", "ParentFirstName2", 1, LocalDate.of(1991, 4, 4), "Country4", "City4", user2, 0, "Nationality4", "Address4", 98765, "Base64Image4");

        TreeNodes treeNodes1 = new TreeNodes(null, parent1, 1, 0);
        TreeNodes treeNodes2 = new TreeNodes(null, parent2, 0, 0);

        return new ArrayList<Object>(Arrays.asList(new Object[][] {
            {new PersonInfo("LastName1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", false, "Nationality1", "Address1", 12345, "Base64Image1"), user1, parent1, parent2, 1, "Nationality1", "Address1", 12345, "Base64Image1", treeNodes1},
            {new PersonInfo("LastName2", "FirstName2", 1, LocalDate.of(2001, 2, 2), "Country2", "City2", false, "Nationality2", "Address2", 54321, "Base64Image2"), user2, parent2, parent1, 0, "Nationality2", "Address2", 54321, "Base64Image2", treeNodes2},
            {new PersonInfo("LastName3", "FirstName3", 1, LocalDate.of(2002, 3, 3), "Country3", "City3", false, "Nationality3", "Address3", 67890, "Base64Image3"), user1, parent1, null, 1, "Nationality3", "Address3", 67890, "Base64Image3", new TreeNodes()},
            {new PersonInfo("LastName4", "FirstName4", 1, LocalDate.of(2003, 4, 4), "Country4", "City4", false, "Nationality4", "Address4", 98765, "Base64Image4"), user2, parent2, null, 0, "Nationality4", "Address4", 98765, "Base64Image4", new TreeNodes()},
        }));
    } 

    @ParameterizedTest
    @MethodSource("testData")
    void testNodeConstructor(
        PersonInfo personInfo, User createdBy, Node parent1, Node parent2,
        int privacy, String nationality, String address, int postalCode, String profilPictureUrl, TreeNodes treeNodes
    ) {
        Node node = new Node(null,personInfo, createdBy, parent1, parent2, privacy);
        assertAll(() -> {
            assertEquals(personInfo, node.getPersonInfo());
            assertEquals(createdBy, node.getCreatedBy());
            assertEquals(parent1, node.getParent1());
            assertEquals(parent2, node.getParent2());
            assertEquals(privacy, node.getPrivacy());
            assertEquals(nationality, node.getNationality());
            assertEquals(address, node.getAdress());
            assertEquals(postalCode, node.getPostalCode());
            assertEquals(profilPictureUrl, node.getProfilPictureUrl());
        });
    }


    @ParameterizedTest
    @MethodSource("testData")
    void testNodeSetters(
        PersonInfo personInfo, User createdBy, Node parent1, Node parent2,
        int privacy, String nationality, String address, int postalCode, String profilPictureUrl, TreeNodes treeNodes
    ) {
        Node node = new Node(null,personInfo, createdBy, parent1, parent2, privacy);
        PersonInfo newPersonInfo = new PersonInfo("NewLastName", "NewFirstName", 1, LocalDate.of(2000, 1, 1), "NewCountry", "NewCity", false, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        User newCreatedBy = new User("NewUser", "NewFirstName", 1, LocalDate.of(2000, 1, 1), "NewCountry", "NewCity", "NewEmail", "NewPassword", "NewSecurity", "NewPhone", "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node newParent1 = new Node(null,"NewParent", "NewParentFirstName", 1, LocalDate.of(1990, 3, 3), "NewCountry", "NewCity", newCreatedBy, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node Partner = new Node(null,"Partner", "Partner", 1, LocalDate.of(1990, 3, 3), "NewCountry", "NewCity", newCreatedBy, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node exPartners = new Node(null,"exPartners", "exPartners", 1, LocalDate.of(1990, 3, 3), "NewCountry", "NewCity", newCreatedBy, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node siblings = new Node(null,"siblings", "siblings", 1, LocalDate.of(1990, 3, 3), "NewCountry", "NewCity", newCreatedBy, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        
        node.setPersonInfo(newPersonInfo);
        node.setCreatedBy(newCreatedBy);
        node.setParent1(newParent1);
        node.setPrivacy(2); 
        node.setParent2(parent2);
        node.addTreeNodes(treeNodes);
        node.setPartner(Partner);
        node.addExPartners(exPartners);
        node.addSiblings(siblings);
        

        assertAll(() -> {
            assertEquals(newPersonInfo, node.getPersonInfo());
            assertEquals(newCreatedBy, node.getCreatedBy());
            assertEquals(newParent1, node.getParent1());
            assertEquals(2, node.getPrivacy());
            assertEquals(parent2, node.getParent2());
            assertTrue(node.getTreeNodes().contains(treeNodes));
            assertEquals(Partner, node.getPartner());
            assertTrue(node.getExPartners().contains(exPartners));
            assertTrue(node.getSiblings().contains(siblings));
            assertFalse(node.isOrphan());
            assertEquals("NewLastName", node.getLastName());
            assertEquals("NewFirstName", node.getFirstName());
            assertEquals(1, node.getGender());
            assertEquals(LocalDate.of(2000, 1, 1), node.getDateOfBirth());
            assertEquals("NewCountry", node.getCountryOfBirth());
            assertEquals("NewCity", node.getCityOfBirth());
            assertEquals(false, node.isDead());
        });
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testIsOrphan(
        PersonInfo personInfo, User createdBy, Node parent1, Node parent2,
        int privacy, String nationality, String address, int postalCode, String profilPictureUrl, TreeNodes treeNodes
    ) {
        Node nodeWithParents = new Node(null,personInfo, createdBy, parent1, parent2, privacy);
        Node nodeWithoutParents = new Node(null,personInfo, createdBy, null, null, privacy);
        User newCreatedBy = new User("NewUser", "NewFirstName", 1, LocalDate.of(2000, 1, 1), "NewCountry", "NewCity", "NewEmail", "NewPassword", "NewSecurity", "NewPhone", "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node Partner = new Node(null,"Partner", "Partner", 1, LocalDate.of(1990, 3, 3), "NewCountry", "NewCity", newCreatedBy, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node exPartners = new Node(null,"exPartners", "exPartners", 1, LocalDate.of(1990, 3, 3), "NewCountry", "NewCity", newCreatedBy, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node siblings = new Node(null,"siblings", "siblings", 1, LocalDate.of(1990, 3, 3), "NewCountry", "NewCity", newCreatedBy, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
        Node nodeWithParent1 = new Node(null,personInfo, createdBy, parent1, null, privacy);
        Node nodeWithParent2 = new Node(null,personInfo, createdBy, null, parent2, privacy);
        Node nodeWithPartner = new Node(null,personInfo, createdBy, null, null, privacy);
        nodeWithPartner.setPartner(Partner);
        Node nodeWithExPartners = new Node(null,personInfo, createdBy, null, null, privacy);
        Node nodeWithSiblings = new Node(null,personInfo, createdBy, null, null, privacy);

        nodeWithExPartners.addExPartners(exPartners);
        nodeWithSiblings.addSiblings(siblings);
        
        assertFalse(nodeWithParents.isOrphan());
        assertFalse(nodeWithParent1.isOrphan());
    	if(parent2 == null) {
    		assertTrue(nodeWithParent2.isOrphan());
    	} else {
    		assertFalse(nodeWithParent2.isOrphan());
    	}
        assertFalse(nodeWithPartner.isOrphan());
        assertFalse(nodeWithExPartners.isOrphan());
        assertFalse(nodeWithSiblings.isOrphan());
        assertTrue(nodeWithoutParents.isOrphan());
    }
    
}
