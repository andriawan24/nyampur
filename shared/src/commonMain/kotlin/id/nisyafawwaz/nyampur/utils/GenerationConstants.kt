package id.nisyafawwaz.nyampur.utils

object GenerationConstants {
    const val INGREDIENT_EXTRACT_COMMAND =
        "Here are images with ingredients on it, I want you to deeply analyze it and tell me what are the ingredients on that image and return the list of ingredient you found on that images, return only text with json format like this {\"data: [{ \"name\": \"[ingredient name]\" }]} without any other information. If there is no ingredients, just return empty list. Make sure only one dominant ingredient per image, and return it sequentially from the first to the last image"
}
