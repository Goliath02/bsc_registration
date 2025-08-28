<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import CourseTimeFrameInfo from "@/AdminPannel/components/CourseTimeFrameInfo.vue";
import { getHolidayInfo } from "@/service/DateService";
import { getTrainers, getTrainingPlaces } from "@/service/InfoService";

const open = defineModel<boolean>({ required: true });

const courseTitle = ref("");
const courseType = ref("");
const startDate = ref();
const trainingUnits = ref();
const trainer = ref();
const place = ref();

const availableTrainers = ref([]);
const availablePlaces = ref([]);

const dateInfos = ref();

const fetchDates = async () => {
  if (startDate.value && trainingUnits.value) {
    dateInfos.value = await getHolidayInfo(
      startDate.value,
      trainingUnits.value,
    );
  }
};

watch([startDate, trainingUnits], () => {
  fetchDates();
});

onMounted(async () => {
  availableTrainers.value = await getTrainers();
  availablePlaces.value = await getTrainingPlaces();

  console.log("availableTrainers.value", availableTrainers.value);
  console.log("availablePlaces.value", availablePlaces.value);
});
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
      <h1 class="text-4xl font-bold">Add course</h1>

      <InputText
        v-model="courseTitle"
        placeholder="Course title"
        class="w-full"
      />
      <Select
        class="w-full"
        v-model="courseType"
        placeholder="Course type"
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
          placeholder="Start date"
          dateFormat="dd.mm.yy"
        />
        <InputNumber v-model="trainingUnits" placeholder="Training units" />
      </div>

      <CourseTimeFrameInfo v-model="dateInfos" />

      <Select
        class="w-full"
        v-model="trainer"
        placeholder="Trainer"
        :options="['Kevin', 'Julia', 'Jeremy']"
      />
      <Select
        class="w-full"
        v-model="place"
        placeholder="Place"
        :options="['Konrad-Adenauer', 'Fritz-Erler', 'Eutingen']"
      />

      <div class="flex gap-4 w-full justify-center items-center">
        <Button @click="open = false" class="px-4 py-2">Back</Button>
        <Button @click="open = false" class="px-4 py-2">Save</Button>
      </div>
    </div>
  </Dialog>
</template>

<style scoped></style>
