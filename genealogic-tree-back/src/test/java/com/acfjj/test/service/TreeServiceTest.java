package com.acfjj.test.service;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.User;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TreeServiceTest {

    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    @Autowired
    private TreeService treeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private NodeService nodeService;

    
    @Test
    @Order(1)
    public void testGetTreesByName() {
    	Tree tree = new Tree("tree_de_merde", 0);
    	tree.setViewOfMonth(3);
    	tree.setViewOfYear(5);
        treeService.addTree(tree);

        String treeName = tree.getName();
        Tree treeSameName = treeService.getTreeByName(treeName);

        assertNotNull(tree);
        assertTrue(treeSameName != null);
    }
    
    @Test
    @Order(2)
    public void testAddTree() {
        Tree tree = createTestTree();
        treeService.addTree(tree);

        Tree retrievedTree = treeService.getTree(tree.getId());

        assertNotNull(retrievedTree);
        assertEquals(tree.getName(), retrievedTree.getName());
    }

    @Test
    @Order(3)
    public void testGetPublicTrees() {
        List<Tree> publicTrees = treeService.getPublicTrees();

        assertNotNull(publicTrees);
        assertTrue(publicTrees.size() > 0);
        
        Tree publicTree = treeService.getTree(1);
        publicTree.setPrivacy(1);
        treeService.addTree(publicTree);
        List<Tree> publicTrees2 = treeService.getPublicTrees();
        
        assertNotNull(publicTrees);
        assertTrue(publicTrees2.size() > publicTrees.size());
    }
    
    @Test
    @Order(4)
    public void testAddNodeToTree() {
        Tree tree = treeService.getTreeByName("TDM");
        Node node = createTestNode();
        nodeService.addNode(node);
        node = nodeService.getNodeByNameAndBirthInfo("User1","FirstName1", LocalDate.of(2000, 1, 1), "Country1", "City1");
        int privacy = 0;
        int depth = 1;

        treeService.addNodeToTree(tree, node, privacy, depth);

        Tree retrievedTree = treeService.getTree(tree.getId());
        assertNotNull(retrievedTree);
        
        Node node2 = new Node(null,userService.getUser(2).getPersonInfo(), userService.getUser(1), null, null, 0);
        nodeService.addNode(node2);
        node2 = nodeService.getNodeByNameAndBirthInfo("User2","FirstName2", LocalDate.of(2001, 2, 2), "Country2", "City2");
        
        treeService.addNodeToTree(tree, node2, 1, 2);
        retrievedTree = treeService.getTree(tree.getId());

        Set<TreeNodes> treeNodes = retrievedTree.getTreeNodes();
        assertNotNull(treeNodes);
        assertFalse(treeNodes.isEmpty());
        assertTrue(treeNodes.size() ==  2);
    }
    
  @Test
  @Order(5)
  public void testRemoveNodeFromTree() {
      Tree tree = treeService.getTreeByName("TDM");
      Node node = nodeService.getNode((long) 2);

      Set<TreeNodes> treeNodesBeforeRemoval = tree.getTreeNodes();
      assertNotNull(treeNodesBeforeRemoval);
      assertFalse(treeNodesBeforeRemoval.isEmpty());

      treeService.removeNodeFromTree(tree, node);

      Tree retrievedTreeAfterRemoval = treeService.getTree(tree.getId());
      assertNotNull(retrievedTreeAfterRemoval);

      Set<TreeNodes> treeNodesAfterRemoval = retrievedTreeAfterRemoval.getTreeNodes();
      assertNotNull(treeNodesAfterRemoval);
      assertEquals(treeNodesAfterRemoval.size(), 1);
  }
  
@Test
@Order(6)
public void testUpdateTree() {
    Tree tree = treeService.getTree(2);
    Node node = nodeService.getNode((long) 2);
    node.setPrivacy(0);

    Tree updatedTree = tree;
    updatedTree.setViewOfMonth(18);
    updatedTree.setName("UpdatedTreeName");
    treeService.removeNodeFromTree(tree, node);
    TreeNodes treeNodes = new TreeNodes(null, node, 1, 4);
    updatedTree.addTreeNodes(treeNodes);

    treeService.updateTree(tree.getId(), updatedTree);

    Tree retrievedTree = treeService.getTree(tree.getId());
    assertNotNull(retrievedTree);
    assertEquals("UpdatedTreeName", retrievedTree.getName());
    assertTrue(updatedTree.getNodes().stream().anyMatch(treeNode -> treeNode.equals(node)));
    assertTrue(updatedTree.getNodes().stream().anyMatch(treeNode -> treeNode.getId().equals(nodeService.getNode((long) 1).getId())));
}
    
    
  @Test
  @Order(8)
  public void testDeleteTree() {
      Tree tree = treeService.getTree(2);

      Long treeId = tree.getId();
      treeService.deleteTree(treeId);
      Tree retrievedTree = treeService.getTree(treeId);
      Node node = nodeService.getNode((long) 1);

      assertNull(retrievedTree);
      assertFalse(node.getTreeNodes().stream().anyMatch(treeNode -> treeNode.getTree() != null && treeNode.getTree().getId().equals(treeId)));
  }

  
  @Test
  public void testGetAllTrees() {
  	List<Tree> trees = treeService.getAllTrees();

      assertFalse(trees.isEmpty());
  }


    private Tree createTestTree() {
        Tree treeTest = new Tree("TDM", 1);
        treeTest.setViewOfMonth(1);
        treeTest.setViewOfYear(5);
        return treeTest;
    }
    private Node createTestNode() {
    	User user1 = new User("User1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", "email1", "password1", "Security1", "Phone1", "Nationality1", "Address1", 12345, "Base64Image1");
        User user2 = new User("User2", "FirstName2", 1, LocalDate.of(2001, 2, 2), "Country2", "City2", "email2", "password2", "Security2", "Phone2", "Nationality2", "Address2", 54321, "Base64Image2");
        
        userService.addUser(user1);
        user1 = userService.getUserByNameAndBirthInfo("User1","FirstName1", LocalDate.of(2000, 1, 1), "Country1", "City1");
        userService.addUser(user2);
        user2 = userService.getUserByNameAndBirthInfo("User2","FirstName2", LocalDate.of(2001, 2, 2), "Country2", "City2");

        return new Node(null,user1.getPersonInfo(), user2, null, null, 0);
    }
}
