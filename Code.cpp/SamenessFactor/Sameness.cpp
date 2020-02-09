#include <stdio.h>

#define MARKER 1

/* Helper function for printing a string that may include markers. */
void println(const char* s) {
    for (int i = 0; s[i] != 0; i++) {
        if (s[i] != 1) {
            printf("%c", s[i]);
        }
    }
    printf("\n");
}

/* Forward iterator for a string that may include markers.
   Given a valid index in the string, it skips all the
   marker characters and returns the index of the first
   non-marker character in the string (may be '\0') */
int fwd(int i, char* s) {
    while (s[i] == MARKER) {
        i++;
    }
    return i;
}

/* Tester for the "end-of-string" condition.
   Returns 1 (true) if the given index points to the string
   terminator and 0 (false) otherwise. */
int done(int i, char* s) {
    return s[i] == 0 ? 1 : 0;
}

/* Deletes (marks) all the "like" characters on the same
   index in the strings. Function is skipping markers when
   it advances through the strings.
   Returns 1 (true) if any change was operated, 0 (false) otherwise.*/
int deleteLikeChars(char* s1, char* s2) {
    int i = fwd(0, s1);
    int j = fwd(0, s2);
    int changed = 0;

    while (!done(i, s1) && !done(j, s2)) {
        if (s1[i] == s2[j]) {
            s1[i] = MARKER;
            s2[j] = MARKER;
            changed = 1;
        }
        i = fwd(i + 1, s1);
        j = fwd(j + 1, s2);
    }

    return changed;
}

/* Advances in the strings until a "similar" condition is met:
   The character at a given index in the string is "like" the
   character in at the next index location in the other string.
   Function is skipping markers when advancing through the strings.
   Returns 1 (true) if any change was operated, 0 (false) otherwise.*/
int deleteFirstSimilar(char* s1, char* s2) {
    int i = fwd(0, s1);
    int j = fwd(0, s2);
    int changed = 0;

    while (!changed && (!done(i, s1) || !done(j, s2))) {
        int ni = done(i, s1) ? i : fwd(i + 1, s1);
        int nj = done(j, s2) ? j : fwd(j + 1, s2);
        if (!done(nj, s2) && s1[i] == s2[nj]) {
            s2[j] = MARKER;
            changed = 1;
        }
        else if (!done(ni, s1) && s2[j] == s1[ni]) {
            s1[i] = MARKER;
            changed = 1;
        }
        else {
            i = ni;
            j = nj;
        }
    }
    return changed;
}

/* Calculates and returns the "Sameness Factor". */
int calculateSFactor(char* s1, char* s2) {
    int i = fwd(0, s1);
    int j = fwd(0, s2);
    int sFactor = 0;

    // add the difference of characters for the ones that line up (overlap)
    while (!done(i, s1) && !done(j, s2)) {
        sFactor += s1[i] - s2[j];
        i = fwd(i + 1, s1);
        j = fwd(j + 1, s2);
    }

    // add the number of characters potentially extending on the first string
    while (!done(i, s1)) {
        sFactor++;
        i = fwd(i + 1, s1);
    }

    // add the number of characters potentially extending on the second string
    while (!done(j, s2)) {
        sFactor++;
        j = fwd(j + 1, s2);
    }

    return sFactor;
}

void main() {
    printf("Hello World!\n");
    char string1[] = "MASSACHUSETTSBAYCOLONY";
    char string2[] = "MINUTEMANNATIONALHISTORICALPARK";
    println("________________Init");
    println(string1);
    println(string2);

    int changed = 0;
    do {
        println("________________deleteLike");
        changed = deleteLikeChars(string1, string2);
        println(string1);
        println(string2);

        println("________________deleteFirstSimilar");
        changed = changed && deleteFirstSimilar(string1, string2);
        println(string1);
        println(string2);
    } while (changed == 1);

    int sFactor = calculateSFactor(string1, string2);
    printf("~~~~~~~~~~~~~~~~Sameness factor = %d\n", sFactor);
}