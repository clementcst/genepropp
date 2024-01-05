package com.acfjj.test.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        Node node1 = new Node(null,"Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", null, 1, "French", "Some Address", 12345, "Base64Image");
        Node node2 = new Node(null,"Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", null, 1, "NewNationality", "NewAddress", 99999, "NewBase64Image");
//        Node node3 = new Node("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvel-Zélande", "Paris Hilton", null, 1, "Kiwi", "Yet Another Address", 67890, "Base64Image3");

        TreeNodes nodes1 = new TreeNodes(null, node1, 1, 0);
        TreeNodes nodes2 = new TreeNodes(null, node2, 0, 0);
        
        long viewOfMonth1 = 3;
        long viewOfMonth2 = 5;
        
        long viewOfYear1 = 3;
        long viewOfYear2 = 5;

        return new ArrayList<Object>(Arrays.asList(new Object[][] {
            {"Tree1", 1, nodes1, viewOfMonth1, viewOfYear2},
            {"Tree2", 0, nodes2, viewOfMonth1, viewOfYear2},
            {"Tree3", 2, new TreeNodes(), viewOfMonth2, viewOfYear1},
            {"Tree4", 1, new TreeNodes(), viewOfMonth1, viewOfYear2},
            {"Tree5", 0, new TreeNodes(), viewOfMonth2, viewOfYear1}
        }));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testTreeConstructor(String name, int privacy, TreeNodes nodes,long viewOfMonth, long viewOfYear) {
        Tree tree = new Tree(name, privacy);
        assertAll(() -> {
            assertEquals(name, tree.getName());
            assertEquals(privacy, tree.getPrivacy());
            assertEquals(viewOfMonth, tree.getViewOfMonth());
            assertEquals(viewOfYear, tree.getViewOfYear());
        });
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testTreeSetters(String name, int privacy, TreeNodes nodes, long viewOfMonth, long viewOfYear) {
        String nameTest = "TreeTestName";
        int privacyTest = 2; 

        Tree tree = new Tree();
        tree.setViewOfMonth(viewOfMonth);

        tree.setName(nameTest);
        assertEquals(nameTest, tree.getName());

        tree.setPrivacy(privacyTest);
        assertEquals(privacyTest, tree.getPrivacy());
        
        tree.setViewOfMonth(viewOfMonth);
        assertEquals(viewOfMonth, tree.getViewOfMonth());
        
        tree.setViewOfYear(viewOfYear);
        assertEquals(viewOfYear, tree.getViewOfYear());
        
        tree.addTreeNodes(nodes);
        assertTrue(tree.getTreeNodes().contains(nodes));

    }

    @ParameterizedTest
    @MethodSource("testData")
    void testIsTreePublic(String name, int privacy, TreeNodes nodes, long viewOfMonth, long viewOfYear) {
        Tree publicTree = new Tree("PublicTree", 1, nodes);
        Tree privateTree = new Tree("PrivateTree", 0, nodes);

        assertTrue(publicTree.isPublic());
        assertFalse(privateTree.isPublic());
    }

}
