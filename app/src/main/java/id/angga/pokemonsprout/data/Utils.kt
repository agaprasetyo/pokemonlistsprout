package id.angga.pokemonsprout.data

fun cleanupDescriptionText(
    text: String,
): String {
    return text
        .replace("\n", " ")
        .replace("\u000c", " ")
        .replace("POKéMON", "pokemon")
}