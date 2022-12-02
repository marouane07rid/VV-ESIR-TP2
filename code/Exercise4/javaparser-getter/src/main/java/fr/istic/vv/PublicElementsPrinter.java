package fr.istic.vv;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class PublicElementsPrinter extends VoidVisitorWithDefaults<Void> {

    List<VariableDeclarator> varPrivate = new ArrayList<VariableDeclarator>();
    private List<String> methodPublic = new ArrayList<String>();
    private List<String> no_getter = new ArrayList<String>();

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for (TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        String class_name = declaration.getNameAsString();
        String package_name = declaration.findCompilationUnit().get().getPackageDeclaration().get().getNameAsString();

        for (MethodDeclaration method : declaration.getMethods()) {
            if (declaration.isPublic()) {
                methodPublic.add(declaration.getNameAsString().toLowerCase());
            }
            method.accept(this, arg);
        }
        // Printing nested types in the top level
        for (BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration)
                member.accept(this, arg);
        }
        for (FieldDeclaration field : declaration.getFields()) {
            if (field.isPrivate()) {
                varPrivate.add(field.getVariable(0));
            }
            field.accept(this, arg);
        }

        fillNoGetter(package_name,class_name);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        // if (declaration.isPrivate()) {
        //     methodPublic.add(declaration.getNameAsString().toLowerCase());
        // }
        //visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(FieldDeclaration declaration, Void arg) {
        // if (declaration.isPrivate()) {
        //     for (VariableDeclarator variable : declaration.getVariables()) {
        //         varPrivate.add(variable);
        //     }
        // }
        
    }

    public void fillNoGetter(String package_name, String class_name) {
        for (VariableDeclarator var : varPrivate) {
            if (!methodPublic.contains("get" + var.getNameAsString()))
                no_getter.add("name: "+var.getNameAsString()+"; class: "+class_name+"; package: "+package_name);
        }
    }

    public void createFile() throws FileNotFoundException, UnsupportedEncodingException {
        List<String> to_write = getNoGetter();

        PrintWriter writer = new PrintWriter("no_getter.txt", "UTF-8");
        writer.println("Private field without getter :\n");
        for (String line : to_write) {
            writer.println(line);
        }
        writer.close();
    }

    public List<VariableDeclarator> getVar() {
        return varPrivate;
    }

    public List<String> getNoGetter() {
        return no_getter;
    }

}
