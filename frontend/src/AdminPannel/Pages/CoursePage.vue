<script setup lang="ts">
import { Button } from "primevue";
import CourseCard from "@/AdminPannel/components/CourseCard.vue";
import { ref } from "vue";
import AddCourseDialog from "@/AdminPannel/components/AddCourseDialog.vue";
import { useQuery } from "@tanstack/vue-query";
import { apiClient } from "@/apiClient";

const isOpen = ref(false);

const getCourses = async () => {
  const {data} = await apiClient.get("/api/course/all", {
    headers: {
      "Content-Type": "application/json",
    },
    withCredentials: true,
  });
  return data;
};

const {data, isLoading, error} = useQuery({
  queryKey: ["courses"],
  queryFn: getCourses,
});

const courses = ref(data);

const openDialog = () => {
  isOpen.value = true;
};
</script>

<template>
  <AddCourseDialog v-model="isOpen" />
  <main class="flex flex-col max-h-screen h-[92vh] md:h-[98vh]">
    <div class="flex justify-between mx-8 mt-4">
      <h2 class="text-2xl font-bold">Courses</h2>
      <Button @click="openDialog" label="Add Course" icon="pi pi-plus" />
    </div>

    <div
      class="bg-bsc-gray overflow-y-auto rounded-lg p-4 flex flex-wrap gap-4 m-4 flex-1 items-center-safe justify-center-safe"
    >

      <div v-if="isLoading" class="w-full h-48 flex justify-center items-center" >
        <ProgressSpinner
          style="
            width: 3em;
            height: 3em;
            --p-progressspinner-color-one: #fff;
            --p-progressspinner-color-two: #fff;
            --p-progressspinner-color-three: #fff;
            --p-progressspinner-color-four: #fff;
          "
          strokeWidth="8"
          animationDuration="1.5s"
        />
      </div>
      <div v-else-if="courses.length === 0" class="w-full h-48 flex justify-center items-center" >
        <h2 class="text-2xl font-bold text-[#888888]">Es gibt noch keine Kurse</h2>
      </div>
      <CourseCard v-else
        v-for="course of courses"
        :key="course.title + course.fromDate"
        :title="course.title"
        :imagePath="course.imagePath"
        :trainer="course.trainer"
        :status="course.status"
        :fromDate="course.fromDate"
        :toDate="course.toDate"
        :totalUnits="course.totalUnits"
        :unitsDone="course.unitsDone"
        :place="course.place"
        :participants="course.participants"
        :maxParticipants="course.maxParticipants"
        class="w-72"
      />
    </div>
  </main>
</template>

<style scoped></style>
