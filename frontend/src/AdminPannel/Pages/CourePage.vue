<script setup lang="ts">
import { Button } from 'primevue'
import CourseCard from "@/AdminPannel/components/CourseCard.vue";
</script>

<template>
  <div class="flex flex-col h-full">
    <div class="flex justify-between mx-8 mt-4">
      <h2 class="text-2xl font-bold">Courses</h2>
      <Button label="Add Course" icon="pi pi-plus" />
    </div>

    <div class="flex-1 m-4 overflow-auto">
      <div class="bg-bsc-gray rounded-lg h-full overflow-clip p-4">

        <CourseCard/>


      </div>
    </div>
  </div>
</template>

<style scoped>

</style>