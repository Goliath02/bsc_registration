<script setup lang="ts">

import { useRoute } from "vue-router";
import { apiClient } from "@/apiClient";
import { useQuery } from "@tanstack/vue-query";
import { formatDate } from "@/utils/dateUtil";
import { ref } from "vue";
import DeleteCourseDialog from "@/AdminPannel/components/DeleteCourseDialog.vue";
import AddCourseDialog from "@/AdminPannel/components/AddCourseDialog.vue";
import { CourseType, getCourseTypes, getTrainers, getTrainingPlaces, getTrainingPlaceById, gerCourseTypeById,
  getTrainerById, Place, Trainer } from "@/service/InfoService";

const route = useRoute();

const {data: availableTrainers, isLoading: trainersLoading} = useQuery<Trainer[]>({
  queryKey: ['trainers'],
  queryFn: getTrainers
})

const {data: availablePlaces, isLoading: placesLoading} = useQuery({
  queryKey: ['trainingPlaces'],
  queryFn: getTrainingPlaces
})

const {data: availableCourseTypes, isLoading: courseTypesLoading} = useQuery({
  queryKey: ['courseTypes'],
  queryFn: getCourseTypes
})


const courseId = route.params.id;

const isDeleteDialogOpen = ref(false);
const isChangeDialogOpen = ref(false);

export type CourseDetails = {
  courseId: number
  courseName: string
  courseTypeId: number
  startDate: Date
  endDate: string
  numberOfMaxParticipants: number
  trainingUnits: number
  courseStatus: string
  courseOwnerId: number
  placeId: number
}


const getCourseDetails = async (): CourseDetails => {
  return (await apiClient.get("/api/course/" + courseId)).data;
}


const { data: courseDetails, isLoading } = useQuery<CourseDetails>({
  queryKey: ['courseDetails', courseId],
  queryFn: getCourseDetails
})

</script>

<template>

  <div v-if="isLoading" class="bg-bsc-gray overflow-y-auto rounded-lg p-4 flex flex-wrap gap-4 m-4 flex-1 items-center-safe justify-center-safe flex flex-col max-h-screen h-[92vh] md:h-[98vh]">
    <ProgressSpinner
      style="
            width: 4em;
            height: 4em;
            --p-progressspinner-color-one: #fff;
            --p-progressspinner-color-two: #fff;
            --p-progressspinner-color-three: #fff;
            --p-progressspinner-color-four: #fff;
          "
      strokeWidth="8"
      animationDuration="1.5s"
    />
  </div>


  <main v-else class="flex flex-col max-h-screen h-[92vh] md:h-[98vh]">
    <DeleteCourseDialog v-model="isDeleteDialogOpen" />
    <AddCourseDialog v-model="isChangeDialogOpen"
                     :is-change="true"
                     :course-title="courseDetails.courseName"
                     :course-type="gerCourseTypeById(courseDetails.courseTypeId, availableCourseTypes).courseTypeName"
                     :start-date="courseDetails.startDate"
                     :training-units="courseDetails.trainingUnits"
                     :max-participants="courseDetails.numberOfMaxParticipants"
                     :course-status="courseDetails.courseStatus"
                     :trainer="getTrainerById(courseDetails.courseOwnerId, availableTrainers).trainerName"
                     :place="getTrainingPlaceById(courseDetails.placeId, availablePlaces).name"
    />
    <div class="flex justify-between mx-8 mt-4">
    </div>

    <div
      class="bg-bsc-gray overflow-y-auto rounded-lg p-4 flex flex-col gap-4 m-4 flex-1"
    >

      <div class="flex justify-between items-center">
        <h1 class="text-3xl font-bold">{{courseDetails.courseName}}</h1>

        <div class="flex gap-4">
          <Button label="Ändern" icon="pi pi-pencil" @click="isChangeDialogOpen = true" />
          <Button label="Löschen" icon="pi pi-trash" @click="isDeleteDialogOpen = true" />
        </div>
      </div>

      <div class="flex gap-16">

        <div class="flex flex-col gap-4">
          <div>{{getTrainerById(courseDetails.courseOwnerId).trainerName}}</div>
          <div>{{formatDate(courseDetails.startDate)}}</div>
        </div>

        <div class="flex flex-col gap-4">
          <div>{{getTrainingPlaceById(courseDetails.placeId).name}}</div>
          <div>{{formatDate(courseDetails.endDate)}}</div>
        </div>

      </div>

    </div>
  </main>
</template>