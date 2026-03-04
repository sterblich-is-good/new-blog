<template>
    <div class="about-container">
        <section class="intro-section">
            <h2 class="section-label">// WHO AM I</h2>
            <p class="bio-text">
                我是 <span class="highlight">Sterblich</span>。
                主攻后端开发，本网站旨在记录个人成长。
            </p>
        </section>

        <section class="stack-section">
            <h2 class="section-label">// RECENTLY USED TECHNOLOGIES </h2>
            <div class="tags">
                <span class="tag" v-for="tech in techs" :key="tech.id">{{ tech.tech }}</span>
            </div>
        </section>

        <section class="contact-section">
            <h2 class="section-label">// CONTACT</h2>
            <div class="contact-links">
                <a href="mailto:sterblich0605@gmail.com" class="contact-item">Email</a>
                <a href="https://github.com/sterblich-is-good" class="contact-item">GitHub</a>
            </div>
        </section>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { currentGET } from '../api'
let techs = ref()

onMounted(async () => {
    const res = await currentGET('url4');
    techs.value = res.data.array
})
</script>

<style scoped>
.about-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 80px 20px;
    color: var(--text-color);
    line-height: 1.6;
}

.section-label {
    font-size: 0.75rem;
    letter-spacing: 3px;
    color: var(--post-excerpt);
    margin-bottom: 24px;
}

section {
    margin-bottom: 80px;
}

.bio-text {
    font-size: 1.8rem;
    font-weight: 500;
    word-break: break-all;
    color: var(--text-color);
}

.highlight {
    background: linear-gradient(90deg, var(--accent-color), #00d1ff);
    -webkit-background-clip: text;
    background-clip: text;
    -webkit-text-fill-color: transparent;
    color: var(--accent-color);
}

.text-stroke {
    -webkit-text-stroke: 1px #1a1a1a;
    color: transparent;
}

.tags {
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
}

.tag {
    padding: 6px 16px;
    border: 1px solid var(--border-color);
    border-radius: 4px;
    font-size: 0.9rem;
    transition: all 0.3s ease;
    color: var(--text-color);
}

.tag:hover {
    background: var(--text-color);
    color: var(--bg-color);
    border-color: var(--text-color);
}

.contact-links {
    display: flex;
    gap: 30px;
}

.contact-item {
    font-size: 1.2rem;
    font-weight: 700;
    text-decoration: none;
    color: var(--text-color);
    position: relative;
}

.contact-item::after {
    content: '';
    position: absolute;
    width: 0;
    height: 2px;
    bottom: -4px;
    left: 0;
    background-color: var(--accent-color);
    transition: width 0.3s ease;
}

.contact-item:hover::after {
    width: 100%;
}
</style>