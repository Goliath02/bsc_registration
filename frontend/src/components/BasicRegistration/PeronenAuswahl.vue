<script setup>
const modelValue = defineModel();
const props = defineProps({
  isNotValid: Boolean
})
</script>

<template>
  <div>

    <div class="w-full">
      <div class="font-bold text-[1.2em]">Wen möchten Sie anmelden?</div>
      <select v-model="modelValue" :class="props.isNotValid ? 'border-2 border-red-700' : '' "
              class="w-full h-[3em] px-[1em] bg-[#585858] rounded-lg font-medium align-middle"
              value="Bitte auswählen">
        <option>Standard-Anmeldung</option>
        <option>Schüler/Student über 18</option>
      </select>
    </div>
  </div>
</template>

<style scoped>

</style>