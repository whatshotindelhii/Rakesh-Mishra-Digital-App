package com.example.data

import com.example.R
import com.example.model.BookPublication
import com.example.model.ImpactMetric
import com.example.model.InsightArticle
import com.example.model.ServiceCategory
import com.example.model.ServiceOffering

object PortfolioRepository {

    const val OFFICIAL_WEBSITE_URL = "https://irakeshmishra.in"
    const val VENTURE_NAME = "Rakesh Ranjan Services • Digital Rakesh"
    const val LOCATION_LABEL = "New Delhi, India"

    val availableBudgetTiers: List<String> = listOf(
        "Startup / Launch",
        "Growth Scale",
        "Enterprise Advisory"
    )

    val availableInquiryServices: List<String> = listOf(
        "Performance & Paid Media",
        "Scent & Sensory Branding",
        "Technical SEO & CRO",
        "AI Workflow Automation",
        "Personal Brand Advisory"
    )

    fun getImpactMetrics(): List<ImpactMetric> = listOf(
        ImpactMetric(
            id = "experience",
            figure = "17+ Yrs",
            label = "Digital Strategy",
            detail = "Cross-channel B2B, D2C & Enterprise execution"
        ),
        ImpactMetric(
            id = "ventures",
            figure = "360°",
            label = "Growth & Scent",
            detail = "Merging measurable performance ROI with sensory recall"
        ),
        ImpactMetric(
            id = "author",
            figure = "2 Books",
            label = "Published Author",
            detail = "Instagram Growth & 6-Pillar Life Management"
        ),
        ImpactMetric(
            id = "columns",
            figure = "TOI",
            label = "Featured Columnist",
            detail = "Commentary on PPC, AI, and consumer behavior"
        )
    )

    fun getServiceOfferings(): List<ServiceOffering> = listOf(
        ServiceOffering(
            id = "svc_paid_media",
            title = "Performance & Paid Media",
            subtitle = "Google Ads, Meta & LinkedIn B2B Scaling",
            category = ServiceCategory.PAID_MEDIA,
            summary = "Full-funnel paid acquisition engineered for unit economics, lower CAC, and predictable pipeline growth across search, social, and B2B decision-makers.",
            deliverables = listOf(
                "Multi-channel media architecture (Google, Meta, LinkedIn)",
                "High-intent funnel segmentation & retargeting matrices",
                "Attribution auditing & weekly ROAS optimization sprints",
                "Creative testing frameworks for D2C & B2B lead capture"
            ),
            targetIndustries = listOf("D2C eCommerce", "B2B SaaS", "Healthcare", "EdTech"),
            outcomeMetric = "ROI-Engineered Media",
            heroImageRes = R.drawable.img_digital_growth_1794992810981
        ),
        ServiceOffering(
            id = "svc_scent_branding",
            title = "Scent & Sensory Branding",
            subtitle = "Signature Olfactory Positioning for Brands",
            category = ServiceCategory.SCENT_BRANDING,
            summary = "Bespoke ambient fragrance and sensory brand strategy that elevates dwell time, emotional memory, and premium brand perception in physical spaces.",
            deliverables = listOf(
                "Brand olfactory profiling & signature scent blueprint",
                "In-store & lobby diffusion strategy for customer recall",
                "Multi-sensory experiential touchpoint alignment",
                "Sensory storytelling for luxury & hospitality launches"
            ),
            targetIndustries = listOf("Hospitality", "Luxury Retail", "Wellness & Spas", "Healthcare"),
            outcomeMetric = "Memorable Brand Recall",
            heroImageRes = R.drawable.img_scent_branding_1794992810188
        ),
        ServiceOffering(
            id = "svc_seo_cro",
            title = "Technical SEO & CRO",
            subtitle = "Search Architecture & Conversion Engineering",
            category = ServiceCategory.SEO_CRO,
            summary = "Compounding organic acquisition paired with landing-page conversion optimization so existing traffic converts into qualified revenue.",
            deliverables = listOf(
                "Core Web Vitals, crawl budget & schema graph architecture",
                "High-intent topical authority clusters & local SEO",
                "Landing page UX teardowns & A/B conversion hypotheses",
                "Lead-form friction reduction & CRM handoff tracking"
            ),
            targetIndustries = listOf("Enterprise", "Regional Multi-Location", "B2B Services", "Retail"),
            outcomeMetric = "Compounding Organic",
            heroImageRes = R.drawable.img_digital_growth_1794992810981
        ),
        ServiceOffering(
            id = "svc_ai_automation",
            title = "AI & Workflow Automation",
            subtitle = "Autonomous Outreach & CRM Pipelines",
            category = ServiceCategory.AI_AUTOMATION,
            summary = "Practical AI agents and automated marketing workflows that eliminate manual bottlenecks across lead qualification, nurturing, and reporting.",
            deliverables = listOf(
                "AI-assisted lead enrichment & instant follow-up routing",
                "Automated CRM pipelines & multi-step nurture sequences",
                "Executive KPI reporting dashboards & anomaly alerts",
                "Custom prompt workflows for content & campaign QA"
            ),
            targetIndustries = listOf("Agencies", "B2B Sales Teams", "High-Volume D2C", "Consultancies"),
            outcomeMetric = "24/7 Pipeline Velocity",
            heroImageRes = R.drawable.img_hero_banner_1794992809841
        )
    )

    fun getBooks(): List<BookPublication> = listOf(
        BookPublication(
            id = "book_instagram",
            title = "The Ultimate Guide to Instagram Marketing",
            subtitle = "Audience Building, Personal Branding & Organic Conversion",
            releaseContext = "Digital Growth & Creator Playbook",
            synopsis = "A practical blueprint for founders, creators, and SMEs looking to transform Instagram attention into measurable brand equity and inbound leads.",
            keyTakeaways = listOf(
                "Positioning your profile bio and visual grid for immediate trust",
                "Repeatable content pillars that balance reach with conversion",
                "Community engagement loops and DM-to-pipeline workflows",
                "Pairing organic storytelling with targeted Meta amplification"
            ),
            focusTag = "Social Growth"
        ),
        BookPublication(
            id = "book_life_pillars",
            title = "6 Pillars of The Life Management System",
            subtitle = "Clarity, Execution & Sustainable Leadership",
            releaseContext = "Personal Systems & Mindset",
            synopsis = "A structured framework connecting daily habits, professional discipline, energy management, and purposeful goal execution for modern builders.",
            keyTakeaways = listOf(
                "Aligning career ambition with resilient daily routines",
                "Decision-making clarity under high-growth pressure",
                "Measuring weekly progress across health, craft, and relationships",
                "Building compounding momentum without burnout"
            ),
            focusTag = "Life Systems"
        )
    )

    fun getInsights(): List<InsightArticle> = listOf(
        InsightArticle(
            id = "ins_ppc_ai",
            title = "Performance Marketing in the Era of AI Bidding & Creative Fatigue",
            sourceLabel = "Column • Performance Strategy",
            readTime = "4 min read",
            category = "Paid Media",
            summary = "Why modern ad algorithms reward offer clarity, first-party conversion signals, and creative velocity over micro-targeting hacks.",
            keyPoints = listOf(
                "Feed algorithms clean offline conversion & qualified-lead signals.",
                "Refresh hooks every 14–21 days to protect blended CPA.",
                "Align ad promise directly with above-the-fold landing page proof."
            ),
            webPath = OFFICIAL_WEBSITE_URL
        ),
        InsightArticle(
            id = "ins_scent_recall",
            title = "Why Sensory & Scent Branding Outlasts Visual-Only Campaigns",
            sourceLabel = "Advisory Note • Sensory Branding",
            readTime = "5 min read",
            category = "Scent Branding",
            summary = "Olfactory cues bypass analytical filters and anchor brand memories in hospitality, retail, and healthcare spaces.",
            keyPoints = listOf(
                "Scent links directly to emotional memory and perceived luxury.",
                "Consistent ambient notes increase customer dwell time and comfort.",
                "Combine physical sensory identity with cohesive digital storytelling."
            ),
            webPath = OFFICIAL_WEBSITE_URL
        ),
        InsightArticle(
            id = "ins_workflow_crm",
            title = "Bridging Lead Generation and Revenue with Automated CRM Workflows",
            sourceLabel = "Playbook • AI Automation",
            readTime = "4 min read",
            category = "Automation",
            summary = "Most campaigns don't fail at the click—they leak revenue in the first 5 minutes after a prospect submits a form.",
            keyPoints = listOf(
                "Automate instant acknowledgment and qualification scoring.",
                "Route high-intent inquiries directly to calendar booking.",
                "Track lead-to-close velocity alongside top-of-funnel CPC."
            ),
            webPath = OFFICIAL_WEBSITE_URL
        )
    )
}
