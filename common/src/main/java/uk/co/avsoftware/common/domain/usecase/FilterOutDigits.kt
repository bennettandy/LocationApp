package uk.co.avsoftware.common.domain.usecase

class FilterOutDigits {
    operator fun invoke(text: String): String = text.filter { it.isDigit() }
}
