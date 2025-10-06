<script setup lang="ts">
import {Button} from "primevue";
import {h, ref} from "vue";
import {apiClient} from "@/apiClient";
import {useQuery} from "@tanstack/vue-query";
import PageTemplate from "@/AdminPannel/Pages/PageTemplate.vue";


const isOpen = ref(false);

const getTrainingPlaces = async () => {
  const {data} = await apiClient.get("/api/place/all", {
    headers: {
      "Content-Type": "application/json",
    },
    withCredentials: true,
  });
  return data;
};

const {data, isLoading, error} = useQuery({
  queryKey: ["trainingPlace"],
  queryFn: getTrainingPlaces,
});

const courses = ref(data);

const openDialog = () => {
  isOpen.value = true;
};


const buttonVNode = h(Button, {
  label: "Add Course",
  icon: "pi pi-plus",
  onClick: () => console.log("Dialog öffnen...")
});


</script>

<template>

<PageTemplate title="TariningPlaces" :action-button="buttonVNode">

  <template #action>
    <Button @click="openDialog" label="Add Course" icon="pi pi-plus" />
  </template>

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
      <h2 class="text-2xl font-bold text-[#888888]">Es gibt noch keine Orte</h2>
    </div>



<!--    <CourseCard v-else-->
<!--                v-for="course of courses"-->
<!--                :key="course.courseId"-->
<!--                :course-id="course.courseId"-->
<!--                :course-name="course.courseName"-->
<!--                :course-owner-name="course.courseOwnerName"-->
<!--                :status="course.courseStatus"-->
<!--                :start-date="course.startDate"-->
<!--                :end-date="course.endDate"-->
<!--                :training-units="course.trainingUnits"-->
<!--                :unitsDone="0"-->
<!--                :place-name="course.placeName"-->
<!--                :participants="0"-->
<!--                :number-of-max-participants="course.numberOfMaxParticipants"-->
<!--                class="w-72"-->
<!--    />-->
  </div>

</PageTemplate>

</template>

<style scoped></style>
