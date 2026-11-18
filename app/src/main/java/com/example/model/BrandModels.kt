package com.example.model

import androidx.annotation.DrawableRes

enum class ServiceCategory(val label: String) {
    ALL("All"),
    PAID_MEDIA("Paid Media"),
    SCENT_BRANDING("Scent Branding"),
    SEO_CRO("SEO & CRO"),
    AI_AUTOMATION("AI Automation")
}

data class ImpactMetric(
    val id: String,
    val figure: String,
    val label: String,
    val detail: String
)

data class ServiceOffering(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: ServiceCategory,
    val summary: String,
    val deliverables: List<String>,
    val targetIndustries: List<String>,
    val outcomeMetric: String,
    @DrawableRes val heroImageRes: Int
)

data class BookPublication(
    val id: String,
    val title: String,
    val subtitle: String,
    val releaseContext: String,
    val synopsis: String,
    val keyTakeaways: List<String>,
    val focusTag: String
)

data class InsightArticle(
    val id: String,
    val title: String,
    val sourceLabel: String,
    val readTime: String,
    val category: String,
    val summary: String,
    val keyPoints: List<String>,
    val webPath: String
)

data class ConsultationFormState(
    val fullName: String = "",
    val companyName: String = "",
    val email: String = "",
    val selectedServices: Set<String> = setOf("Performance & Paid Media"),
    val budgetTier: String = "Growth Scale",
    val projectNotes: String = "",
    val validationError: String? = null
)

sealed interface WebPortalStatus {
    data object Loading : WebPortalStatus
    data object Ready : WebPortalStatus
    data class Error(val description: String) : WebPortalStatus
}

data class WebPortalState(
    val currentUrl: String = "https://irakeshmishra.in",
    val pageTitle: String = "irakeshmishra.in",
    val progress: Int = 0,
    val canGoBack: Boolean = false,
    val status: WebPortalStatus = WebPortalStatus.Loading
)

data class BrandUiState(
    val isDarkThemeOverride: Boolean? = null,
    val selectedCategory: ServiceCategory = ServiceCategory.ALL,
    val metrics: List<ImpactMetric> = emptyList(),
    val services: List<ServiceOffering> = emptyList(),
    val books: List<BookPublication> = emptyList(),
    val insights: List<InsightArticle> = emptyList(),
    val activeServiceDetail: ServiceOffering? = null,
    val activeBookDetail: BookPublication? = null,
    val activeInsightDetail: InsightArticle? = null,
    val consultationForm: ConsultationFormState = ConsultationFormState(),
    val portalState: WebPortalState = WebPortalState(),
    val statusBannerMessage: String? = null
) {
    val filteredServices: List<ServiceOffering>
        get() = if (selectedCategory == ServiceCategory.ALL) {
            services
        } else {
            services.filter { it.category == selectedCategory }
        }
}
