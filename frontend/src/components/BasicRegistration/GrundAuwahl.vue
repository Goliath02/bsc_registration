<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";

let courses = ref([]);

const modelValue = defineModel();
const props = defineProps({
  isNotValid: Boolean
})

onMounted(() => {

  axios.get(useRegistrationStore().getTargetURL() + "/courses").then((res) => {
    courses.value = res.data;
  }).catch((err) => {
    console.log(err);
  })
})

</script>

<template>
  <div class="w-full">
    <div class="font-bold text-[1.2em]">Aus welchen Grund registrieren Sie die Person?</div>
    <select v-model="modelValue" :class="props.isNotValid ? 'border-2 border-red-700' : '' "
            class="w-full h-[3em] px-[1em]  bg-[#585858] rounded-lg font-medium" value="Bitte auswählen">

      <option v-for="course in courses">{{ course }}</option>
    </select>
  </div>
</template>

<style scoped>

</style>