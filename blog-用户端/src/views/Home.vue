<template>
    <div class="home-container">
        <header class="hero">
            <h1 class="title">{{ title }}</h1>
            <p class="subtitle">{{ subtitle }}</p>
            <button class="primary-btn">开始探索</button>
        </header>
    </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { currentGET } from '../api'

let title = ref('')
let subtitle = ref('')

onMounted(async () => {
    const res = await currentGET('url3');
    title.value = res.data.title;
    subtitle.value = res.data.subtitle
})

</script>

<style scoped>
.home-container {
    max-width: 900px;
    margin: 0 auto;
    padding: 60px 20px;
    font-family: 'Inter', -apple-system, sans-serif;
    color: #333;
}

.hero {
    text-align: center;
    margin-top: 85px;
    margin-bottom: 80px;
}

.title {
    font-size: 3rem;
    font-weight: 800;
    letter-spacing: -1px;
    margin-bottom: 20px;
    background: linear-gradient(45deg, #1a1a1a, #4a4a4a);
    -webkit-background-clip: text;
}

.subtitle {
    font-size: 1.2rem;
    color: #666;
    margin-bottom: 40px;
}

.primary-btn {
    padding: 12px 32px;
    background-color: #000;
    color: #fff;
    border: none;
    border-radius: 50px;
    cursor: pointer;
    transition: transform 0.2s ease;
}

.primary-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}
</style>