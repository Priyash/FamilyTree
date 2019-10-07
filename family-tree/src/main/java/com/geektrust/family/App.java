package com.geektrust.family;

import java.util.LinkedList;
import java.util.Scanner;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.GraphInterface.IBuildable;

/**
 * Hello world!
 */
public final class App {

    private static final Scanner scanner = new Scanner(System.in);
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Enter the full file path : ");
        String filePath = scanner.nextLine();

        IBuildable internalBuilder = new InternalGraphBuilder();
        internalBuilder.build();

        if (!filePath.isEmpty()) {
            IBuildable externalBuilder = new ExternalGraphBuilder();
            externalBuilder.buildWithExternalFile(filePath);
            LinkedList<IFamilyMember> familyTree = Graph.getInstance().getLatestFamilyTree();
        }

    }
}
