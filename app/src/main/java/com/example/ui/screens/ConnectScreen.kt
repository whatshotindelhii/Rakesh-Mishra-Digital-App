package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.PortfolioRepository
import com.example.model.ConsultationFormState
import com.example.ui.components.SectionHeading

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConnectScreen(
    formState: ConsultationFormState,
    onFullNameChanged: (String) -> Unit,
    onCompanyChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onToggleService: (String) -> Unit,
    onSelectBudget: (String) -> Unit,
    onNotesChanged: (String) -> Unit,
    onBuildValidatedBrief: () -> Pair<String, String>?,
    onShowMessage: (String) -> Unit,
    onOpenLivePortal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 840.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionHeading(
                        eyebrow = PortfolioRepository.LOCATION_LABEL,
                        title = stringResource(R.string.connect_header_title)
                    )
                    Text(
                        text = stringResource(R.string.connect_header_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Surface(
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        OutlinedTextField(
                            value = formState.fullName,
                            onValueChange = onFullNameChanged,
                            label = { Text("Your Name *") },
                            placeholder = { Text("e.g. Aarav Sharma") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("connect_name_input")
                        )

                        OutlinedTextField(
                            value = formState.companyName,
                            onValueChange = onCompanyChanged,
                            label = { Text("Brand / Company") },
                            placeholder = { Text("e.g. Lumina Hospitality") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("connect_company_input")
                        )

                        OutlinedTextField(
                            value = formState.email,
                            onValueChange = onEmailChanged,
                            label = { Text("Reply Email") },
                            placeholder = { Text("name@company.com") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("connect_email_input")
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Focus Areas",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                PortfolioRepository.availableInquiryServices.forEach { svc ->
                                    val selected = svc in formState.selectedServices
                                    FilterChip(
                                        selected = selected,
                                        onClick = { onToggleService(svc) },
                                        leadingIcon = if (selected) {
                                            {
                                                Icon(
                                                    imageVector = Icons.Filled.Check,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        } else null,
                                        label = { Text(svc) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                        ),
                                        modifier = Modifier.testTag("connect_service_chip_${svc.hashCode()}")
                                    )
                                }
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Engagement Scope",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                PortfolioRepository.availableBudgetTiers.forEach { tier ->
                                    val selected = formState.budgetTier == tier
                                    FilterChip(
                                        selected = selected,
                                        onClick = { onSelectBudget(tier) },
                                        label = { Text(tier) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        modifier = Modifier.testTag("connect_budget_chip_${tier.hashCode()}")
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = formState.projectNotes,
                            onValueChange = onNotesChanged,
                            label = { Text("Goals & Timeline") },
                            placeholder = { Text("Share target KPIs, brand launch dates, or challenges...") },
                            minLines = 3,
                            maxLines = 5,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("connect_notes_input")
                        )

                        formState.validationError?.let { errorMsg ->
                            Text(
                                text = errorMsg,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        Button(
                            onClick = {
                                val payload = onBuildValidatedBrief() ?: return@Button
                                val (subject, body) = payload
                                val mailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse(
                                        "mailto:?subject=${Uri.encode(subject)}&body=${Uri.encode(body)}"
                                    )
                                }
                                val launched = runCatching {
                                    context.startActivity(mailIntent)
                                }.isSuccess
                                if (!launched) {
                                    val fallbackShare = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_SUBJECT, subject)
                                        putExtra(Intent.EXTRA_TEXT, body)
                                    }
                                    val shared = runCatching {
                                        context.startActivity(
                                            Intent.createChooser(fallbackShare, "Send Brief")
                                        )
                                    }.isSuccess
                                    if (!shared) {
                                        onShowMessage("No email app detected; use Live Website booking below.")
                                    }
                                }
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("connect_send_email_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = stringResource(R.string.action_send_email),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    val payload = onBuildValidatedBrief() ?: return@OutlinedButton
                                    val (subject, body) = payload
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_SUBJECT, subject)
                                        putExtra(Intent.EXTRA_TEXT, body)
                                    }
                                    runCatching {
                                        context.startActivity(
                                            Intent.createChooser(shareIntent, "Share Consultation Brief")
                                        )
                                    }.onFailure {
                                        onShowMessage("Unable to open share sheet on this device.")
                                    }
                                },
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("connect_share_brief_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.size(6.dp))
                                Text(
                                    text = stringResource(R.string.action_share_brief),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }

                            OutlinedButton(
                                onClick = onOpenLivePortal,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("connect_open_portal_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Language,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.size(6.dp))
                                Text(
                                    text = stringResource(R.string.hero_cta_live_site),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
