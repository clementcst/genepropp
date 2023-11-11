package com.acfjj.test.service;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.repository.NodeRepository;
import com.acfjj.app.repository.TreeRepository;
import com.acfjj.app.service.TreeService;
import com.acfjj.test.CustomTestWatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class TreeServiceTest {

    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    @Autowired
    private TreeService treeService;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Test
    public void testAddTree() {
        Tree tree = createTestTree();
        treeService.addTree(tree);

        Tree retrievedTree = treeService.getTree(tree.getId());

        assertNotNull(retrievedTree);
        assertEquals(tree.getName(), retrievedTree.getName());
    }

    @Test
    public void testDeleteTree() {
        Tree tree = createTestTree();
        treeService.addTree(tree);

        Long treeId = tree.getId();
        treeService.deleteTree(treeId);

        Tree retrievedTree = treeService.getTree(treeId);

        assertNull(retrievedTree);
    }

    @Test
    public void testUpdateTree() {
        Tree tree = createTestTree();
        treeService.addTree(tree);

        Tree updatedTree = createTestTree();
        updatedTree.setId(tree.getId());
        updatedTree.setName("UpdatedTreeName");

        treeService.updateTree(tree.getId(), updatedTree);

        Tree retrievedTree = treeService.getTree(tree.getId());

        assertNotNull(retrievedTree);
        assertEquals(updatedTree.getName(), retrievedTree.getName());
    }

    @Test
    public void testGetTreesByName() {
        Tree tree = createTestTree();
        treeService.addTree(tree);

        String treeName = tree.getName();
        Tree treeSameName = treeService.getTreeByName(treeName);

        assertNotNull(tree);
        assertTrue(treeSameName != null);
    }

    @Test
    public void testGetPublicTrees() {
        Tree publicTree = createTestTree();
        publicTree.setPrivacy(1);
        treeService.addTree(publicTree);

        List<Tree> publicTrees = treeService.getPublicTrees();

        assertNotNull(publicTrees);
        assertTrue(publicTrees.size() > 0);
    }

    @Test
    public void testDeleteNodeFromTree() {
        Tree tree = createTestTree();
        treeService.addTree(tree);

        TreeNodes treeNodes = tree.getNodes().iterator().next();
        Long nodeId = treeNodes.getNode().getId();
        Long treeId = tree.getId();

        treeService.deleteNodeFromTree(nodeId, treeId);

        Tree retrievedTree = treeService.getTree(treeId);
        assertNull(retrievedTree);
    }

    private Tree createTestTree() {
        return new Tree("TestTree", 0);
    }
}
