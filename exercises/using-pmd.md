# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

## Answer

Nous avons choisis d'analyser avec PMD le projet d'apache/commons-cli, nous avons observé une recommandation à propos du code ci-dessous.

    ```private boolean isLongOption(final String token) {
        if (token == null || !token.startsWith("-") || token.length() == 1) {
            return false;
        }

        final int pos = token.indexOf("=");
        final String t = pos == -1 ? token : token.substring(0, pos);

        if (!getMatchingLongOptions(t).isEmpty()) {
            // long or partial long options (--L, -L, --L=V, -L=V, --l, --l=V)
            return true;
        }
        if (getLongPrefix(token) != null && !token.startsWith("--")) {
            // -LV
            return true;
        }

        return false;
    }```

Il y a possibilité d'éviter les deux conditions IF, en retournant directement la valeur booléenne des conditions.

    ```private boolean isLongOption(final String token) {
        if (token == null || !token.startsWith("-") || token.length() == 1) {
            return false;
        }

        final int pos = token.indexOf("=");
        final String t = pos == -1 ? token : token.substring(0, pos);

        return (!getMatchingLongOptions(t).isEmpty() || getLongPrefix(token) != null && !token.startsWith("--"));
    }```


La seconde recommandation que nous avons choisi est "uncommented empty method body" à la ligne 36 du fichier BasicParserTest.java. Voici la fonction en question:

```@Override
    @Test
    @Ignore("not supported by the BasicParser")
    public void testAmbiguousLongWithoutEqualSingleDash() throws Exception {
    }```
    
    La fonction n'est en effet pas commentée mais elle possède des annotations qui ne sont pas considérées comme des commentaires par pmd, mais qui fournissent pourtant des informations sur la fonction (ex: "not supported by the BasicParser"). Il n'y a ici aucune modification à faire.
