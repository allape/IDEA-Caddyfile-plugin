<!-- https://vuejs.org/guide/introduction.html#single-file-components -->

<script setup>
import {ref} from 'vue'

const count = ref(0)
</script>

<template>
  <button @click="count++">Count is: {{ count }}</button>
</template>

<style scoped>
button {
  font-weight: bold;
}
</style>