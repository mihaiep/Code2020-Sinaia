#include <stdio.h>

#define FALSE 0
#define TRUE 1
#define END 0

// const array of truth values:
// A:   1100             B: 1111           C: 0110             D: 0000
//      1100                1111              0110                1111
//      1100                0000              0110                1111
//      1100 -> 0xCCCC      0000 -> 0xFF00    0110 -> 0x6666      0000 -> 0x0FF0
const unsigned short TRUTH[] = { 0xCCCC, 0xFF00, 0x6666, 0x0FF0 };

unsigned short calcTerm(const char* expression, int iFrom, int iTo) {
    // start with all bits set to 1, then as we read the operands one by one
    // we'll *and* its value with the operand's truth value. 
    unsigned short resultTerm = 0xFFFF;

    int negate = FALSE;
    for (int i = iFrom; i < iTo; i++) {
        // if found a "~", remember we'll negate the truth value and then move on.
        if (expression[i] == '~') {
            negate = TRUE;
        }
        else {
            // find the index of the truth in the TRUTH array.
            // Assumption: expression is valid, so expression[i] is one of 'A', 'B', 'C' or 'D'
            int iTruth = expression[i] - 'A';
            // filter in the result only the bits matching the truth value of this operand.
            resultTerm &= negate ? ~TRUTH[iTruth] : TRUTH[iTruth];
            // reset the negate boolean since we're done with this operand.
            negate = FALSE;
        }
    }

    return resultTerm;
}

unsigned short calcExpression(const char* expression) {
    // start with all bits set to 0, then as we read the term values one by one
    // we'll *or* its value with the term result.
    unsigned short resultExpression = 0;

    int iFrom = 0;
    int iTo = iFrom;
    int done = FALSE;
    while (!done) {
        // move iTo to the next '+' or end of string, whichever comes first
        while (expression[iTo] != '+' && expression[iTo] != END) {
            iTo++;
        }

        // in between iFrom and iTo we have a valid term. Calcualate it's Veitch value and
        // combine it with the final result.
        resultExpression |= calcTerm(expression, iFrom, iTo);

        // if iTo is not the string terminator...
        if (expression[iTo] != END) {
            // ...there's more too scan in the tree. Move iTo past the current char ('+')
            // and align iFrom with it as the new start
            iTo++;
            iFrom = iTo;
        }
        else {
            // ...otherwise we're done!
            done = TRUE;
        }
    }

    return resultExpression;
}

int main() {
    const char* tests[] = {
        // example
        "AB+~C+~A~D",

        // sample input
        "AB+~AB+~A~B",
        "AB~C~D+AB~CD+~A~B~CD",
        "AB~C~D+~AB~C~D+A~B~C~D",
        "B~D+~B~D",
        "~A~BD+~A~B~D",
        "B~D+~A~BD+A~B~C",
        "~B~C+BCD+B~C~D",
        "A~C+ACD+~A~CD",
        "AB~D+~ABD+A~BD+~A~B~D",
        "B~D+~A~CD+~A~B~C~D",
        "AB~C~D+~AB~C~D+A~B~C~D",

        // test input
        "~A~B+AB+~CD+C~D",
        "B~D+AC+~A~B+CD",
        "~ABD+~BCD+D",
        "~A~BD+~A~BD+AC+BD",
        "~ABC~D+A~B~C~D+~A~B~C"
    };

    for (int i = 0; i < sizeof(tests) / sizeof(char*); i++) {
        printf("%04X > \"%s\" \n", calcExpression(tests[i]), tests[i]);
    }
}