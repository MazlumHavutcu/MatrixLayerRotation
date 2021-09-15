package com.demo.matrixlayerrotation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;

@Component
public class MatrixLayerRotation implements CommandLineRunner {

    @Override
    public void run(String... args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);

        int r = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        matrixRotation(matrix, r);

        bufferedReader.close();
    }

    public void matrixRotation(List<List<Integer>> matrix, int r) {

        int matrixRow = matrix.size();
        int matrixColumn = matrix.get(0).size();

        for (int i = 0; i < r; i++) // round count
        {
            List<List<Integer>> temp = new ArrayList();
            copy(temp, matrix);

            for (int j = 0; j < Math.min(matrixRow, matrixColumn) / 2; j++) // circle count
            {
                int layerCount = (matrixRow - j * 2) * 2 + (matrixColumn - j * 2) * 2 - 4; // for all circle remove two number

                int rowUpperLimit = matrixRow - 1 - j;
                int columnUpperLimit = matrixColumn - 1 - j;

                int currentRowIndex = j;
                int currentColumnIndex = j;
                boolean down = true;
                boolean right = false;
                boolean up = false;
                boolean left = false;

                for (int m = 0; m < layerCount; m++) {

                    if (currentRowIndex == rowUpperLimit && currentColumnIndex == j) {
                        down = false;
                        right = true;
                    }
                    if (currentRowIndex == rowUpperLimit && currentColumnIndex == columnUpperLimit) {
                        right = false;
                        up = true;
                    }
                    if (currentRowIndex == j && currentColumnIndex == columnUpperLimit) {
                        up = false;
                        left = true;
                    }
                    if (down) {
                        temp.get(currentRowIndex + 1).set(currentColumnIndex, matrix.get(currentRowIndex).get(currentColumnIndex));
                        currentRowIndex = currentRowIndex + 1;
                    }
                    if (right) {
                        temp.get(currentRowIndex).set(currentColumnIndex + 1, matrix.get(currentRowIndex).get(currentColumnIndex));
                        currentColumnIndex = currentColumnIndex + 1;
                    }
                    if (up) {
                        temp.get(currentRowIndex - 1).set(currentColumnIndex, matrix.get(currentRowIndex).get(currentColumnIndex));
                        currentRowIndex = currentRowIndex - 1;
                    }
                    if (left) {
                        temp.get(currentRowIndex).set(currentColumnIndex - 1, matrix.get(currentRowIndex).get(currentColumnIndex));
                        currentColumnIndex = currentColumnIndex - 1;
                    }
                }
            }
            copy(matrix, temp);
        }
        System.out.println(matrix);
    }

    public void copy(List<List<Integer>> dest, List<List<Integer>> source) {
        dest.clear();
        for (int i = 0; i < source.size(); i++) {
            dest.add(new ArrayList<>(source.get(i)));
        }
    }
}
