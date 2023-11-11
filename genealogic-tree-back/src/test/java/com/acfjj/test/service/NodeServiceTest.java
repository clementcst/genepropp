package com.acfjj.test.service;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.User;
import com.acfjj.app.repository.NodeRepository;
import com.acfjj.app.repository.PersonInfoRepository;
import com.acfjj.app.repository.TreeRepository;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.UserService;
import com.acfjj.test.CustomTestWatcher;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class NodeServiceTest {

    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    @Autowired
    private NodeService nodeService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private TreeRepository treeRepository;

    @Test
    @Order(1)
    public void testAddNode() {
        Node node = createTestNode();
        System.out.println(node.getPersonInfo());
        nodeService.addNode(node);

        Node retrievedNode = nodeService.getNode(node.getId());

        assertNotNull(retrievedNode);
        assertEquals(node.getPersonInfo().getLastName(), retrievedNode.getPersonInfo().getLastName());
    }

//    @Test
    @Order(2)
    public void testAddParent() {
        Node parentNode = createTestNode();
        Node childNode = createTestNode();
        nodeService.addNode(parentNode);
        nodeService.addNode(childNode);

        nodeService.addParent(childNode, parentNode);

        Node retrievedChildNode = nodeService.getNode(childNode.getId());

        assertNotNull(retrievedChildNode.getParent1());
        assertEquals(parentNode.getId(), retrievedChildNode.getParent1().getId());
    }

//    @Test
    @Order(3)
    public void testUpdateParent() {
        Node parentNode = createTestNode();
        Node childNode = createTestNode();
        nodeService.addNode(parentNode);
        nodeService.addNode(childNode);

        nodeService.addParent(childNode, parentNode);

        Node newParentNode = createTestNode();
        nodeService.addNode(newParentNode);

        nodeService.updateParent(childNode.getId(), newParentNode.getId(), 1);

        Node retrievedChildNode = nodeService.getNode(childNode.getId());

        assertNotNull(retrievedChildNode.getParent1());
        assertEquals(newParentNode.getId(), retrievedChildNode.getParent1().getId());
    }

//    @Test
    @Order(4)
    public void testDeleteNode() {
        Node node = createTestNode();
        nodeService.addNode(node);

        Long nodeId = node.getId();
        nodeService.deleteNode(nodeId);

        Node retrievedNode = nodeService.getNode(nodeId);

        assertNull(retrievedNode);
    }

//    @Test
    @Order(5)
    public void testUpdateNode() {
        Node node = createTestNode();
        nodeService.addNode(node);

        Node updatedNode = createTestNode();
        updatedNode.setId(node.getId());
        updatedNode.getPersonInfo().setLastName("UpdatedLastName");

        nodeService.updateNode(node.getId(), updatedNode);

        Node retrievedNode = nodeService.getNode(node.getId());

        assertNotNull(retrievedNode);
        assertEquals(updatedNode.getPersonInfo().getLastName(), retrievedNode.getPersonInfo().getLastName());
    }

//    @Test
    @Order(6)
    public void testGetParentsOfNode() {
    	User user1 = new User("User1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", "email1", "password1", "Security1", "Phone1", "Nationality1", "Address1", 12345, "Base64Image1");
        User user2 = new User("User2", "FirstName2", 1, LocalDate.of(2001, 2, 2), "Country2", "City2", "email2", "password2", "Security2", "Phone2", "Nationality2", "Address2", 54321, "Base64Image2");
    	Node parent1 = new Node("Parent1", "ParentFirstName1", 1, LocalDate.of(1990, 3, 3), "Country3", "City3", user1, 1, "Nationality3", "Address3", 67890, "Base64Image3");
        Node parent2 = new Node("Parent2", "ParentFirstName2", 1, LocalDate.of(1991, 4, 4), "Country4", "City4", user2, 0, "Nationality4", "Address4", 98765, "Base64Image4");
        Node childNode = createTestNode();

        nodeService.addNode(parent1);
        nodeService.addNode(parent2);
        nodeService.addNode(childNode);

        nodeService.addParent(childNode, parent1);
        nodeService.addParent(childNode, parent2);

        List<Node> parents = nodeService.getParentsOfNode(childNode.getId());

        assertNotNull(parents);
        assertEquals(2, parents.size());
    }

//    @Test
    @Order(7)
    public void testDoesNodeBelongToTree() {
        Node node = createTestNode();
        Tree tree = createTestTree();

        nodeService.addNode(node);
        treeRepository.save(tree);

        TreeNodes treeNodes = new TreeNodes(tree, node, 0, 0);
        Set<TreeNodes> treeNodesSet = new HashSet<>(Arrays.asList(treeNodes));
        node.setTrees(treeNodesSet);
        nodeService.updateNode(node.getId(), node);

        boolean belongsToTree = nodeService.doesNodeBelongToTree(node.getId(), tree.getId());

        assertTrue(belongsToTree);
    }

    private Node createTestNode() {
    	User user1 = new User("User1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", "email1", "password1", "Security1", "Phone1", "Nationality1", "Address1", 12345, "Base64Image1");
        User user2 = new User("User2", "FirstName2", 1, LocalDate.of(2001, 2, 2), "Country2", "City2", "email2", "password2", "Security2", "Phone2", "Nationality2", "Address2", 54321, "Base64Image2");
        
        System.out.println(user1.getPersonInfo());
        userService.addUser(user1);
        user1 = userService.getUserByNameAndBirthInfo("User1","FirstName1", LocalDate.of(2000, 1, 1), "Country1", "City1");
        System.out.println(user1.getPersonInfo());
        userService.addUser(user2);
        user2 = userService.getUserByNameAndBirthInfo("User2","FirstName2", LocalDate.of(2001, 2, 2), "Country2", "City2");

        return new Node(user1.getPersonInfo(), user2, null, null, 0);
    }

    private Tree createTestTree() {
        Node node = new Node("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", null, 1, "French", "Some Address", 12345, "Base64Image");
        Set<TreeNodes> nodes = new HashSet<>();
        nodes.add(new TreeNodes(null, node, 1, 0));
        long viewOfMonth = 3;
        long viewOfYear = 5;
        
        
        Tree treeTest = new Tree("Tree1", 1);
        treeTest.setNodes(nodes);
        treeTest.setViewOfMonth(viewOfMonth);
        treeTest.setViewOfYear(viewOfYear);
        
        return treeTest;
    }
}
