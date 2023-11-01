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
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.Node;
import com.acfjj.test.CustomTestWatcher;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class TreeTest {
    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    static public List<Object> testData() {
        Node node1 = new Node("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", null, 1, "French", "Some Address", 12345, "Base64Image");
        Node node2 = new Node("Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", null, 2, "Peïsian", "Another Address", 54321, "Base64Image2");
        Node node3 = new Node("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvel-Zélande", "Paris Hilton", null, 1, "Kiwi", "Yet Another Address", 67890, "Base64Image3");

        Set<TreeNodes> nodes1 = new HashSet<>();
        Set<TreeNodes> nodes2 = new HashSet<>();
        nodes1.add(new TreeNodes(null, node1, 1, 0));
        nodes2.add(new TreeNodes(null, node2, 0, 0));
        nodes2.add(new TreeNodes(null, node3, 0, 0));

        return new ArrayList<Object>(Arrays.asList(new Object[][] {
            {"Tree1", 1, nodes1},
            {"Tree2", 0, nodes2},
            {"Tree3", 2, new HashSet<>()},
            {"Tree4", 1, new HashSet<>()},
            {"Tree5", 0, new HashSet<>()}
        }));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testTreeConstructor(String name, int privacy, Set<TreeNodes> nodes) {
        Tree tree = new Tree(name, privacy, nodes);
        assertNotNull(tree.getNodes());
        assertAll(() -> {
            assertEquals(name, tree.getName());
            assertEquals(privacy, tree.getPrivacy());
        });
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testTreeSetters(String name, int privacy, Set<TreeNodes> nodes) {
        String nameTest = "TreeTestName";
        int privacyTest = 2;

        Tree tree = new Tree(name, privacy, nodes);

        tree.setName(nameTest);
        assertEquals(nameTest, tree.getName());

        tree.setPrivacy(privacyTest);
        assertEquals(privacyTest, tree.getPrivacy());
    }
    
    @ParameterizedTest
    @MethodSource("testData")
    void testAddNodeToTree(String name, int privacy, Set<TreeNodes> nodes) {
        Tree tree = new Tree(name, privacy, nodes);
        Node node1 = new Node("NewNode1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", null, 1, "Nationality1", "Address1", 12345, "Base64Image1");

        tree.addNode(node1);
        assertTrue(tree.getNodes().stream().anyMatch(treeNodes -> treeNodes.getNode().equals(node1)));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testRemoveNodeFromTree(String name, int privacy, Set<TreeNodes> nodes) {
        Tree tree = new Tree(name, privacy, nodes);
        Node node1 = new Node("NewNode1", "FirstName1", 1, LocalDate.of(2000, 1, 1), "Country1", "City1", null, 1, "Nationality1", "Address1", 12345, "Base64Image1");

        tree.addNode(node1);
        assertTrue(tree.getNodes().stream().anyMatch(treeNodes -> treeNodes.getNode().equals(node1)));

        tree.removeNode(node1);
        assertFalse(tree.getNodes().stream().anyMatch(treeNodes -> treeNodes.getNode().equals(node1)));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testIsTreePublic(String name, int privacy, Set<TreeNodes> nodes) {
        Tree publicTree = new Tree("PublicTree", 1, nodes);
        Tree privateTree = new Tree("PrivateTree", 0, nodes);

        assertTrue(publicTree.isTreePublic());
        assertFalse(privateTree.isTreePublic());
    }

}
