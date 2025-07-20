<script setup lang="ts">

import {ref} from "vue";

const props = defineProps({
  open: Boolean
})

const courseTitle = ref('');
const courseType = ref('');
const startDate = ref('');
const trainingUnits = ref('');

const days = ref([]);
const times = ref([]);

</script>

<template>
<Dialog v-model:visible="props.open" modal header="Add Course">
  <h1>Add Course</h1>



</Dialog>
</template>

<style scoped>

</style>