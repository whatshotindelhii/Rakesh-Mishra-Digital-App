package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.PortfolioRepository
import com.example.model.BookPublication
import com.example.model.BrandUiState
import com.example.model.InsightArticle
import com.example.model.ServiceCategory
import com.example.model.ServiceOffering
import com.example.model.WebPortalStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        BrandUiState(
            metrics = PortfolioRepository.getImpactMetrics(),
            services = PortfolioRepository.getServiceOfferings(),
            books = PortfolioRepository.getBooks(),
            insights = PortfolioRepository.getInsights()
        )
    )
    val uiState: StateFlow<BrandUiState> = _uiState.asStateFlow()

    fun toggleTheme(currentlyDark: Boolean) {
        _uiState.update { state ->
            state.copy(isDarkThemeOverride = !currentlyDark)
        }
    }

    fun selectCategory(category: ServiceCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun openServiceDetail(service: ServiceOffering?) {
        _uiState.update { it.copy(activeServiceDetail = service) }
    }

    fun openBookDetail(book: BookPublication?) {
        _uiState.update { it.copy(activeBookDetail = book) }
    }

    fun openInsightDetail(insight: InsightArticle?) {
        _uiState.update { it.copy(activeInsightDetail = insight) }
    }

    fun preselectServiceForInquiry(serviceTitle: String) {
        _uiState.update { state ->
            val updatedServices = state.consultationForm.selectedServices + serviceTitle
            state.copy(
                activeServiceDetail = null,
                consultationForm = state.consultationForm.copy(
                    selectedServices = updatedServices,
                    validationError = null
                )
            )
        }
    }

    fun updateFullName(value: String) {
        _uiState.update { state ->
            state.copy(
                consultationForm = state.consultationForm.copy(
                    fullName = value,
                    validationError = null
                )
            )
        }
    }

    fun updateCompanyName(value: String) {
        _uiState.update { state ->
            state.copy(
                consultationForm = state.consultationForm.copy(
                    companyName = value,
                    validationError = null
                )
            )
        }
    }

    fun updateEmail(value: String) {
        _uiState.update { state ->
            state.copy(
                consultationForm = state.consultationForm.copy(
                    email = value,
                    validationError = null
                )
            )
        }
    }

    fun toggleInquiryService(serviceLabel: String) {
        _uiState.update { state ->
            val current = state.consultationForm.selectedServices
            val updated = if (serviceLabel in current) {
                if (current.size > 1) current - serviceLabel else current
            } else {
                current + serviceLabel
            }
            state.copy(
                consultationForm = state.consultationForm.copy(
                    selectedServices = updated,
                    validationError = null
                )
            )
        }
    }

    fun selectBudgetTier(tier: String) {
        _uiState.update { state ->
            state.copy(
                consultationForm = state.consultationForm.copy(
                    budgetTier = tier,
                    validationError = null
                )
            )
        }
    }

    fun updateProjectNotes(value: String) {
        _uiState.update { state ->
            state.copy(
                consultationForm = state.consultationForm.copy(
                    projectNotes = value,
                    validationError = null
                )
            )
        }
    }

    fun buildValidatedInquiryBody(): Pair<String, String>? {
        val form = _uiState.value.consultationForm
        val trimmedName = form.fullName.trim()
        val trimmedEmail = form.email.trim()

        if (trimmedName.isEmpty()) {
            _uiState.update { state ->
                state.copy(
                    consultationForm = state.consultationForm.copy(
                        validationError = "Please enter your name so Rakesh can address your inquiry."
                    )
                )
            }
            return null
        }

        if (trimmedEmail.isNotEmpty() && (!trimmedEmail.contains("@") || !trimmedEmail.contains("."))) {
            _uiState.update { state ->
                state.copy(
                    consultationForm = state.consultationForm.copy(
                        validationError = "Please enter a valid email address or leave it blank."
                    )
                )
            }
            return null
        }

        val companyLine = form.companyName.trim().ifEmpty { "Not specified" }
        val emailLine = trimmedEmail.ifEmpty { "Provided via sender" }
        val servicesLine = form.selectedServices.joinToString(", ")
        val notesLine = form.projectNotes.trim().ifEmpty { "Interested in discussing growth strategy & scope." }

        val subject = "Strategy Consultation Request — $trimmedName ($companyLine)"
        val body = buildString {
            appendLine("Hello Rakesh,")
            appendLine()
            appendLine("I am reaching out via the irakeshmishra.in mobile companion app to discuss a consultation:")
            appendLine()
            appendLine("• Name: $trimmedName")
            appendLine("• Brand / Company: $companyLine")
            appendLine("• Reply Email: $emailLine")
            appendLine("• Focus Areas: $servicesLine")
            appendLine("• Engagement Tier: ${form.budgetTier}")
            appendLine()
            appendLine("Project Brief:")
            appendLine(notesLine)
        }

        return subject to body
    }

    fun setStatusBanner(message: String?) {
        _uiState.update { it.copy(statusBannerMessage = message) }
    }

    fun onPortalPageStarted(url: String) {
        _uiState.update { state ->
            state.copy(
                portalState = state.portalState.copy(
                    currentUrl = url,
                    status = WebPortalStatus.Loading,
                    progress = 10
                )
            )
        }
    }

    fun onPortalProgressChanged(progress: Int) {
        _uiState.update { state ->
            state.copy(
                portalState = state.portalState.copy(progress = progress)
            )
        }
    }

    fun onPortalPageFinished(url: String, title: String?, canGoBack: Boolean) {
        _uiState.update { state ->
            state.copy(
                portalState = state.portalState.copy(
                    currentUrl = url,
                    pageTitle = title?.takeIf { it.isNotBlank() } ?: "irakeshmishra.in",
                    progress = 100,
                    canGoBack = canGoBack,
                    status = if (state.portalState.status is WebPortalStatus.Error) {
                        state.portalState.status
                    } else {
                        WebPortalStatus.Ready
                    }
                )
            )
        }
    }

    fun onPortalError(description: String) {
        _uiState.update { state ->
            state.copy(
                portalState = state.portalState.copy(
                    status = WebPortalStatus.Error(description)
                )
            )
        }
    }

    fun resetPortalError() {
        _uiState.update { state ->
            state.copy(
                portalState = state.portalState.copy(
                    status = WebPortalStatus.Loading,
                    progress = 15
                )
            )
        }
    }
}
