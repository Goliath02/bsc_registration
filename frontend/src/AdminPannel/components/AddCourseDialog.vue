<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import CourseTimeFrameInfo from "@/AdminPannel/components/CourseTimeFrameInfo.vue";
import { getHolidayInfo } from "@/service/DateService";
import { getTrainers, getTrainingPlaces } from "@/service/InfoService";
import { apiClient } from "@/apiClient";
import { useMutation, useQuery, useQueryClient } from "@tanstack/vue-query";

const open = defineModel<boolean>({ required: true });

const courseTitle = ref("");
const courseType = ref("");
const startDate = ref();
const trainingUnits = ref();
const maxParticipants = ref();
const courseStatus = ref();
const trainer = ref();
const place = ref();

const queryClient = useQueryClient();

const { data: availableTrainers, isLoading: trainersLoading } = useQuery({
  queryKey: ['trainers'],
  queryFn: getTrainers
})

const { data: availablePlaces, isLoading: placesLoading } = useQuery({
  queryKey: ['trainingPlaces'],
  queryFn: getTrainingPlaces
})


const { data: dateInfos, isLoading, isError: isHolidayError } = useQuery({
  queryKey: ['holidayInfo', startDate, trainingUnits],
  queryFn: () => getHolidayInfo(startDate.value, trainingUnits.value),
  enabled: computed(() => !!startDate.value && !!trainingUnits.value)
})

const { mutate: saveCourse, isPending, isSuccess, isError:isSaveCourseError } = useMutation({
  mutationFn: () =>
    apiClient.post("/api/course/create", {
      title: courseTitle.value,
      type: courseType.value,
      fromDate: startDate.value,
      toDate: startDate.value,
      totalUnits: trainingUnits.value,
      trainer: trainer.value,
      place: place.value,
      participants: 0,
      maxParticipants: 0,
      status: "PLANING",
    }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ['courses'] })
  }
})

const trainingStatus = ref([
  { name: 'In Planning', code: 'PLANNING' },
  { name: 'Open', code: 'OPEN' },
  { name: 'Active', code: 'ACTIVE' },
  { name: 'Completed', code: 'COMPLETED' },
]);

</script>

<template>
  <Dialog
    v-model:visible="open"
    modal
    :closable="false"
    close-on-escape
    :draggable="false"
    :show-header="false"
    class="p-4"
  >
    <div class="flex flex-col gap-4 justify-center items-center w-full">
      <h1 class="text-4xl font-bold">Kurs hinzufügen</h1>

      <InputText
        v-model="courseTitle"
        placeholder="Kurs-Titel"
        class="w-full"
      />
      <Select
        class="w-full"
        v-model="courseType"
        placeholder="Kurs Typ"
        :options="['Not Swimmer', 'Swimmer', 'Aqua-Gymnastics']"
      />

      <div class="flex gap-4 md:flex-row flex-col w-full">
        <DatePicker
          v-model="startDate"
          showIcon
          fluid
          :showOnFocus="false"
          showTime
          hourFormat="24"
          placeholder="Start-datum"
          dateFormat="dd.mm.yy"
        />
        <InputNumber v-model="trainingUnits" placeholder="Trainingseinheiten" />
      </div>

      <CourseTimeFrameInfo v-model="dateInfos" :chosen-date="startDate" />

      <div class="flex gap-4 md:flex-row flex-col w-full">
        <InputNumber v-model="maxParticipants" placeholder="Max Teilehmer (Bsp. 5-15)" class="w-full" />

        <Select
          v-model="courseStatus"
          placeholder="Course status"
          :options="trainingStatus"
          optionLabel="name"
          class="w-full"
        />
      </div>

      <Select
        v-model="availableTrainers"
        optionLabel="trainerName"
        placeholder="Trainer"
        :options="['Kevin', 'Julia', 'Jeremy']"
        class="w-full"
      />
      <Select
        class="w-full"
        v-model="place"
        placeholder="Ort"
        :options="['Konrad-Adenauer', 'Fritz-Erler', 'Eutingen']"
      />

      <div class="flex gap-4 w-full justify-center items-center">
        <Button @click="open = false" class="w-24 h-10 px-4 py-2">Zurück</Button>
        <Button @click="saveCourse()" class="w-24 h-10 px-4 py-2">
          <ProgressSpinner
            v-if="isPending"
            style="
            width: 1em;
            height: 1em;
            --p-progressspinner-color-one: #fff;
            --p-progressspinner-color-two: #fff;
            --p-progressspinner-color-three: #fff;
            --p-progressspinner-color-four: #fff;
          "
            strokeWidth="8"
            animationDuration="1.5s"/>
          <p v-else >Speichern</p>
        </Button>
      </div>
    </div>
  </Dialog>
</template>

<style scoped></style>
