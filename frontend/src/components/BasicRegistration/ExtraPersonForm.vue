<script setup>

import ReworkedBSCInput from "@/components/BasicRegistration/ReworkedBSCInput.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";

const extraModelValues = defineModel();
const props = defineProps({
  index: Number,
})
</script>

<template>
  <div class="bg-bsc-lightgray p-[1em] rounded-lg">

    <h3 class="font-bold text-[1.2em]">Extra Person {{ props.index + 1 }}</h3>

    <div class="flex gap-[1.5em]">
      <ReworkedBSCInput v-model.modelValue="extraModelValues.name" header-field="Vorname" input-type="text"/>
      <ReworkedBSCInput v-model.modelValue="extraModelValues.surename" header-field="Nachname" input-type="text"/>
    </div>

    <div class="flex gap-[1.5em]">
      <ReworkedBSCInput v-model.modelValue="extraModelValues.birtday" header-field="Geburtsdatum" input-type="date"/>
      <gender-selection v-model.modelValue="extraModelValues.gender" class="flex-1 w-1/2"/>


    </div>
  </div>
</template>

<style scoped>

</style>