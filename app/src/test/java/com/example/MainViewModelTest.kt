package com.example

import com.example.model.ServiceCategory
import com.example.viewmodel.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MainViewModelTest {

    @Test
    fun `category filter narrows service offerings`() {
        val viewModel = MainViewModel()
        assertEquals(4, viewModel.uiState.value.filteredServices.size)

        viewModel.selectCategory(ServiceCategory.SCENT_BRANDING)
        val filtered = viewModel.uiState.value.filteredServices
        assertEquals(1, filtered.size)
        assertEquals("svc_scent_branding", filtered.first().id)
    }

    @Test
    fun `consultation brief validation rejects blank name and formats valid brief`() {
        val viewModel = MainViewModel()
        assertNull(viewModel.buildValidatedInquiryBody())
        assertNotNull(viewModel.uiState.value.consultationForm.validationError)

        viewModel.updateFullName("Ananya Verma")
        viewModel.updateCompanyName("Aura Resorts")
        viewModel.updateEmail("ananya@auraresorts.in")
        viewModel.preselectServiceForInquiry("Scent & Sensory Branding")

        val payload = viewModel.buildValidatedInquiryBody()
        assertNotNull(payload)
        assertTrue(payload!!.first.contains("Ananya Verma"))
        assertTrue(payload.second.contains("Aura Resorts"))
        assertTrue(payload.second.contains("Scent & Sensory Branding"))
    }

    @Test
    fun `theme toggle flips override state`() {
        val viewModel = MainViewModel()
        viewModel.toggleTheme(currentlyDark = true)
        assertEquals(false, viewModel.uiState.value.isDarkThemeOverride)

        viewModel.toggleTheme(currentlyDark = false)
        assertEquals(true, viewModel.uiState.value.isDarkThemeOverride)
    }
}
