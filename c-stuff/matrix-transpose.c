#include <stdio.h>

void transpose(int array[][3], int n);
void print(int array[][3], int n);

int main() {
    int matrix[3][3] = {{1, 2, 3},
                      {4, 5, 6},
                      {7, 8, 9}};
    print(matrix, 3);
    transpose(matrix, 3);
    print(matrix, 3);
}

void transpose(int array[][3], int n) {
    int i, j;
    for (i = 1; i < n; i ++) {
        int *p1 = array[i];
        int *p2 = array[0] + i;
        for (j = 0; j < i; j ++) {
            int t = *(p1 + j);
            *(p1 + j) = *p2;
            *p2 = t;
            p2 += n;
        }
    }
    printf("\n");
}

void print(int array[][3], int n) {
    int i, j;
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            printf("%d ", array[i][j]);
        }
        printf("\n");
    }
}
