import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.math3.linear.*;

public class Gn_Stuff {

    public static double equationQua(double a, double b, double c, double x) {
        double value = a * Math.pow(x, 2) + b * x + c;
        return value;
    }
    
    public static double equationExp(double a, double b, double c, double x) {
        double value = ((a*(Math.exp(b*x))) + c);
        return value;
    }
    
    public static double equationLog(double a, double b, double c, double x) {
        double value = (a*(Math.log10(b + x)) + c);
        return value;
    }
    
    public static double equationRat(double a, double b, double c, double x) {
        double value = (((a*x)/(x + b)) + c);
        return value;
    }
    
    public static double partialQua(int i, double x) {
        if (i ==  0) {
            return - Math.pow(x, 2);
        }
        if (i == 1) {
            return -x;
        }
        if (i == 2) {
        	return -1;
        }
        double fail = 0.0;
        return fail; // shouldn't get to this
    }
    
    public static double partialExp(int i, double x, double b, double a) {
        if (i == 0) {
            return -Math.exp(b*x);
        }
        if (i == 1) {
            return -(a*x*(Math.exp(b*x)));
        }
        if (i == 2) {
            return -1;
        }
        double fail = 0.0;
        return fail;
    }
    
    public static double partialLog(int i, double x, double b, double a) {
        if (i == 0) {
            return -(Math.log10(b + x));
        }
        if (i == 1) {
            return -(a/((Math.log(10))*(b + x)));
        }
        if (i == 2) {
            return -1;
        }
        double fail = 0.0;
        return fail;
    }
    
    public static double partialRat(int i, double x, double b, double a) {
        if (i == 0) {
            return -(x/(x + b));
        }
        if (i == 1) {
            return ((a*x)/((b + x)*(b + x)));
        }
        if (i == 2) {
            return -1;
        }
        double fail = 0.0;
        return fail;
    }

    public static void gn_qua() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input Filename:");
        String fileName = scan.nextLine();
        String str = "";
        try {
            File inputFile = new File(fileName);
            FileReader reader = new FileReader(inputFile);
            BufferedReader in = new BufferedReader(reader);
            String temp;
            while((temp = in.readLine())!= null) {
                str = str + temp;
                str = str + ",";
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
        
        String[] s1 = str.split(",");
        double[] d1 = new double[s1.length];
        int i = 0;
        for (String s : s1) {
            d1[i] = Double.parseDouble(s);
            i++;
        }
        double[] x = new double[d1.length / 2];
        double[] y = new double[d1.length / 2];
        int k = 0;
        for (int j = 0; j < x.length; j++) {
            x[j] = d1[k];
            k += 2;
        }
        k = 1;
        for (int j1 = 0; j1 < y.length; j1++) {
            y[j1] = d1[k];
            k += 2;
        }

        System.out.println("Guess the parameter A");
        double a0 = scan.nextDouble();

        System.out.println("Guess the parameter B");
        double b0 = scan.nextDouble();
        
        System.out.println("Guess the paraneter C");
        double c0 = scan.nextDouble();
        
        RealVector beta = new ArrayRealVector(new double[] {a0,b0,c0});

        System.out.println("Guess the number of iterations");
        double numberOfIterations = scan.nextDouble();
        
        double[] residual = new double[x.length];
        for (int n = 0; n < x.length; n++) {
            residual[n] = y[n] - equationQua(a0, b0, c0, x[n]);
        }
        RealVector Res = new ArrayRealVector(residual);
        double[][] j = new double[residual.length][3];
        RealMatrix Jacobi = new Array2DRowRealMatrix(j);
        
        for (int m = 0; m < Jacobi.getRowDimension(); m ++) {
            Jacobi.setEntry(m, 0, partialQua(0, x[m]));
            Jacobi.setEntry(m, 1, partialQua(1, x[m]));
            Jacobi.setEntry(m, 2, partialQua(2, x[m]));
        }
        //not finished
        qr_fact_househ qrfact = new qr_fact_househ(Jacobi);
        RealMatrix R = qrfact.getR();
        RealMatrix Q = qrfact.getQ();
        
        System.out.println(Jacobi);
    }
    
    public static void gn_exp() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input Filename:");
        String fileName = scan.nextLine();
        String str = "";
        try {
            File inputFile = new File(fileName);
            FileReader reader = new FileReader(inputFile);
            BufferedReader in = new BufferedReader(reader);
            String temp;
            while((temp = in.readLine())!= null) {
                str = str + temp;
                str = str + " ";
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
        
        String[] s1 = str.split(",");
        double[] d1 = new double[s1.length];
        int i = 0;
        for (String s : s1) {
            d1[i] = Double.parseDouble(s);
            i++;
        }
        double[] x = new double[d1.length / 2];
        double[] y = new double[d1.length / 2];
        int k = 0;
        for (int j = 0; j < x.length; j++) {
            x[j] = d1[k];
            k += 2;
        }
        k = 1;
        for (int j1 = 0; j1 < y.length; j1++) {
            y[j1] = d1[k];
            k += 2;
        }
        
        System.out.println("Guess the parameter A");
        double a0 = scan.nextDouble();

        System.out.println("Guess the parameter B");
        double b0 = scan.nextDouble();
        
        System.out.println("Guess the paraneter C");
        double c0 = scan.nextDouble();
        
        RealVector beta = new ArrayRealVector(new double[] {a0,b0,c0});

        System.out.println("Guess the number of iterations");
        double numberOfIterations = scan.nextDouble();
        
        double[] residual = new double[x.length];
        for (int n = 0; n < x.length; n++) {
            residual[n] = y[n] - equationExp(a0, b0, c0, x[n]);
        }
        RealVector Res = new ArrayRealVector(residual);
        double[][] j = new double[residual.length][3];
        RealMatrix Jacobi = new Array2DRowRealMatrix(j);
        
        for (int m = 0; m < Jacobi.getRowDimension(); m ++) {
            Jacobi.setEntry(m, 0, partialExp(0, x[m], b0, a0));
            Jacobi.setEntry(m, 1, partialExp(1, x[m], b0, a0));
            Jacobi.setEntry(m, 2, partialExp(2, x[m], b0, a0));
        }
        //nothing finished after this
        qr_fact_househ qrfact = new qr_fact_househ(Jacobi);
        RealMatrix R = qrfact.getR();
        RealMatrix Q = qrfact.getQ();
    }
    
    public static void gn_log() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input Filename:");
        String fileName = scan.nextLine();
        String str = "";
        try {
            File inputFile = new File(fileName);
            FileReader reader = new FileReader(inputFile);
            BufferedReader in = new BufferedReader(reader);
            String temp;
            while((temp = in.readLine())!= null) {
                str = str + temp;
                str = str + " ";
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
        
        String[] s1 = str.split(",");
        double[] d1 = new double[s1.length];
        int i = 0;
        for (String s : s1) {
            d1[i] = Double.parseDouble(s);
            i++;
        }
        double[] x = new double[d1.length / 2];
        double[] y = new double[d1.length / 2];
        int k = 0;
        for (int j = 0; j < x.length; j++) {
            x[j] = d1[k];
            k += 2;
        }
        k = 1;
        for (int j1 = 0; j1 < y.length; j1++) {
            y[j1] = d1[k];
            k += 2;
        }
        
        System.out.println("Guess the parameter A");
        double a0 = scan.nextDouble();

        System.out.println("Guess the parameter B");
        double b0 = scan.nextDouble();
        
        System.out.println("Guess the paraneter C");
        double c0 = scan.nextDouble();
        
        RealVector beta = new ArrayRealVector(new double[] {a0,b0,c0});
        
        System.out.println("Guess the number of iterations");
        double numberOfIterations = scan.nextDouble();
        
        double[] residual = new double[x.length];
        for (int n = 0; n < x.length; n++) {
            residual[n] = y[n] - equationLog(a0, b0, c0, x[n]);
        }
        RealVector Res = new ArrayRealVector(residual);
        double[][] j = new double[residual.length][3];
        RealMatrix Jacobi = new Array2DRowRealMatrix(j);
        
        for (int m = 0; m < Jacobi.getRowDimension(); m ++) {
            Jacobi.setEntry(m, 0, partialLog(0, x[m], b0, a0));
            Jacobi.setEntry(m, 1, partialLog(1, x[m], b0, a0));
            Jacobi.setEntry(m, 2, partialLog(2, x[m], b0, a0));
        }
        //nothing finished after this
        qr_fact_househ qrfact = new qr_fact_househ(Jacobi);
        RealMatrix R = qrfact.getR();
        RealMatrix Q = qrfact.getQ();
    }
    
    public static void gn_rat() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input Filename:");
        String fileName = scan.nextLine();
        String str = "";
        try {
            File inputFile = new File(fileName);
            FileReader reader = new FileReader(inputFile);
            BufferedReader in = new BufferedReader(reader);
            String temp;
            while((temp = in.readLine())!= null) {
                str = str + temp;
                str = str + " ";
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();;
        }
        
        String[] s1 = str.split(",");
        double[] d1 = new double[s1.length];
        int i = 0;
        for (String s : s1) {
            d1[i] = Double.parseDouble(s);
            i++;
        }
        double[] x = new double[d1.length / 2];
        double[] y = new double[d1.length / 2];
        int k = 0;
        for (int j = 0; j < x.length; j++) {
            x[j] = d1[k];
            k += 2;
        }
        k = 1;
        for (int j1 = 0; j1 < y.length; j1++) {
            y[j1] = d1[k];
            k += 2;
        }
        
        System.out.println("Guess the parameter A");
        double a0 = scan.nextDouble();

        System.out.println("Guess the parameter B");
        double b0 = scan.nextDouble();
        
        System.out.println("Guess the paraneter C");
        double c0 = scan.nextDouble();
        
        RealVector beta = new ArrayRealVector(new double[] {a0,b0,c0});

        System.out.println("Guess the number of iterations");
        double numberOfIterations = scan.nextDouble();
        
        double[] residual = new double[x.length];
        for (int n = 0; n < x.length; n++) {
            residual[n] = y[n] - equationRat(a0, b0, c0, x[n]);
        }
        RealVector Res = new ArrayRealVector(residual);
        double[][] j = new double[residual.length][3];
        RealMatrix Jacobi = new Array2DRowRealMatrix(j);
        
        for (int m = 0; m < Jacobi.getRowDimension(); m ++) {
            Jacobi.setEntry(m, 0, partialRat(0, x[m], b0, a0));
            Jacobi.setEntry(m, 1, partialRat(1, x[m], b0, a0));
            Jacobi.setEntry(m, 2, partialRat(2, x[m], b0, a0));
        }
        //nothing finished after this
        qr_fact_househ qrfact = new qr_fact_househ(Jacobi);
        RealMatrix R = qrfact.getR();
        RealMatrix Q = qrfact.getQ();
    }
    
    public static void main(String[] args) {
        gn_qua();
    }
}