package com.acfjj.test.service;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.TreeService;
import com.acfjj.app.service.UserService;
import com.acfjj.test.CustomTestWatcher;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class NodeServiceTest {

    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    @Autowired
    private NodeService nodeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TreeService treeService;

    @Test
    @Order(1)
    public void testAddNode() {
        Node node = createTestNode();
        nodeService.addNode(node);

        Node retrievedNode = nodeService.getNode(node.getId());

        assertNotNull(retrievedNode);
        assertEquals(node.getPersonInfo().getLastName(), retrievedNode.getPersonInfo().getLastName());
        assertEquals(node.getPersonInfo().getId(), retrievedNode.getPersonInfo().getId());

    }

    @Test
    @Order(2)
    public void testAddParent() {
    	User user1 = userService.getUser(1);
    	User user3 = new User("User3", "FirstName3", 0, LocalDate.of(2003, 1, 1), "Country3", "City3", "email3", "password3", "Security3", "Phone3", "Nationality3", "Address3", 12345, "Base64Image3");
    	userService.addUser(user3);
        user3 = userService.getUserByNameAndBirthInfo("User3","FirstName3", LocalDate.of(2003, 1, 1), "Country3", "City3");
    	Node parent1 = new Node(null,user3.getPersonInfo(), user1, null, null, 1);  
    	
    	Node childNode = nodeService.getNode((long) 1);
        nodeService.addNode(parent1);
        parent1 = nodeService.getNodeByNameAndBirthInfo("User3", "FirstName3", LocalDate.of(2003, 1, 1), "Country3", "City3");

        nodeService.addParent(childNode, parent1);

        Node retrievedChildNode = nodeService.getNode(childNode.getId());

        assertNotNull(retrievedChildNode.getParent1());
        assertEquals(parent1.getId(), retrievedChildNode.getParent1().getId());
    }

    @Test
    @Order(4)
    public void testUpdateParent() {
        Node parentNode = nodeService.getNodeByNameAndBirthInfo("User3", "FirstName3", LocalDate.of(2003, 1, 1), "Country3", "City3");
        Node childNode = nodeService.getNode((long) 1);

        parentNode.setPrivacy(0);
        nodeService.updateNode(parentNode.getId(), parentNode);
        parentNode = nodeService.getNode((long) 2);

        nodeService.updateParent(childNode.getId(), parentNode.getId(), 1);

        Node retrievedChildNode = nodeService.getNode(childNode.getId());

        assertNotNull(retrievedChildNode.getParent1());
        assertEquals(parentNode.getId(), retrievedChildNode.getParent1().getId());
    }

    @Test
    @Order(6)
    public void testDeleteNode() {
    	PersonInfo person = new PersonInfo("person4", "FirstName4", 1, LocalDate.of(2004, 1, 1), "Country4", "City4", false, "Nationality1", "Address1", 12345, "Base64Image1");
        User user3 = userService.getUserByNameAndBirthInfo("User3","FirstName3", LocalDate.of(2003, 1, 1), "Country3", "City3");
        Node nodeToDel = new Node(null,person, user3, null, null, 0);
        nodeService.addNode(nodeToDel);
        nodeToDel = nodeService.getNodeByNameAndBirthInfo("person4", "FirstName4", LocalDate.of(2004, 1, 1), "Country4", "City4");

        
        
        Long nodeToDelId = nodeToDel.getId();
        assertNotNull(nodeToDel);
        nodeService.deleteNode(nodeToDelId);

        Node retrievedNode = nodeService.getNode(nodeToDelId);
        assertNull(retrievedNode);
    }

    @Test
    @Order(3)
    public void testUpdateNode() {
        Node node = nodeService.getNode((long) 1);

        node.setPrivacy(1);
        node.getPersonInfo().setLastName("UpdatedLastName");

        nodeService.updateNode(node.getId(), node);

        Node retrievedNode = nodeService.getNode(node.getId());

        assertNotNull(retrievedNode);
        assertEquals(node.getPersonInfo().getLastName(), retrievedNode.getPersonInfo().getLastName(), "UpdatedLastName");
        assertEquals(node.getPrivacy(), retrievedNode.getPrivacy());

    }

    @Test
    @Order(5)
    public void testGetParentsOfNode() {
        User user2 = userService.getUser(2);
        User user1 = userService.getUser(1);
        Node parent2 = new Node(null,user2.getPersonInfo(), user1, null, null, 0);
        Node childNode = nodeService.getNode((long) 1);
        
        nodeService.addNode(parent2);
        parent2 = nodeService.getNodeByNameAndBirthInfo("User2", "FirstName2", LocalDate.of(2001, 2, 2), "Country2", "City2");
        nodeService.addParent(childNode, parent2);

        List<Node> parents = nodeService.getParentsOfNode(childNode.getId());

        assertNotNull(parents);
        assertEquals(2, parents.size());
    }

    @Test
    @Order(7)
    public void testDoesNodeBelongToTree() {
        Node node = nodeService.getNode((long) 1);
        Tree tree = createTestTree();
        treeService.addTree(tree);

        treeService.addNodeToTree(tree, node, 1, 1 );
        tree = treeService.getTree(1);
        node = nodeService.getNode((long) 1);        
        
        Node node2 = nodeService.getNode((long) 2);
        boolean belongsToTree = nodeService.doesNodeBelongToTree(node.getId(), tree.getId());
        boolean belongsToTree2 = nodeService.doesNodeBelongToTree(node2.getId(), tree.getId());


        assertTrue(belongsToTree);
        assertFalse(belongsToTree2);
    }
    
    @Test
    @Order(8)
    public void testRemoveLinks() {
        Node node = nodeService.getNode((long) 1);

        assertNotNull(node.getParent1());
        assertNotNull(node.getParent2());
        
//        nodeService.removeLinks(node.getId());
        node = nodeService.getNode((long) 1);
        
        assertNull(node.getParent1());
        assertNull(node.getParent2());

    }

    private Node createTestNode() {
    	User user1 = new User("User1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", "email1", "password1", "Security1", "Phone1", "Nationality1", "Address1", 12345, "Base64Image1");
        User user2 = new User("User2", "FirstName2", 1, LocalDate.of(2001, 2, 2), "Country2", "City2", "email2", "password2", "Security2", "Phone2", "Nationality2", "Address2", 54321, "Base64Image2");
        
        userService.addUser(user1);
        user1 = userService.getUserByNameAndBirthInfo("User1","FirstName1", LocalDate.of(2000, 1, 1), "Country1", "City1");
        userService.addUser(user2);
        user2 = userService.getUserByNameAndBirthInfo("User2","FirstName2", LocalDate.of(2001, 2, 2), "Country2", "City2");

        return new Node(null,user1.getPersonInfo(), user1, null, null, 0);
    }

    private Tree createTestTree() {
        long viewOfMonth = 3;
        long viewOfYear = 5;
        
        
        Tree treeTest = new Tree("Tree1", 1);
        treeTest.setViewOfMonth(viewOfMonth);
        treeTest.setViewOfYear(viewOfYear);
        
        return treeTest;
    }
}
