<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import CourseTimeFrameInfo from "@/AdminPannel/components/CourseTimeFrameInfo.vue";
import {getHolidayInfo} from "@/service/DateService";
import {getCourseTypes, getTrainers, getTrainingPlaces, Trainer} from "@/service/InfoService";
import {apiClient} from "@/apiClient";
import {useMutation, useQuery, useQueryClient} from "@tanstack/vue-query";
import {yupResolver} from "@primevue/forms/resolvers/yup";
import * as yup from "yup";
import {Form} from "@primevue/forms";

const open = defineModel<boolean>({required: true});

const props = defineProps<{
  isChange?: boolean,
  courseTitle?: string,
  courseType?: string,
  startDate?: Date,
  trainingUnits?: number,
  maxParticipants?: number,
  courseStatus?: object,
  trainer?: string,
  place?: string,
}>();


const addCourseDto = ref({
  courseTitle: "",
  courseType: "",
  startDate: undefined,
  trainingUnits: "",
  maxParticipants: "",
  courseStatus: undefined as { name: string, code: string } | undefined,
  trainer: "",
  place: ""
})

onMounted(() => {

  addCourseDto.value.courseTitle = props.courseTitle ?? "";
  addCourseDto.value.courseType = props.courseType ?? "";
  addCourseDto.value.startDate = props.startDate ?? undefined;
  addCourseDto.value.trainingUnits = props.trainingUnits ?? "";
  addCourseDto.value.maxParticipants = props.maxParticipants ?? "";
  addCourseDto.value.courseStatus = props.courseStatus ?? undefined as { name: string, code: string } | undefined;
  addCourseDto.value.trainer = props.trainer ?? "";
  addCourseDto.value.place = props.place ?? "";
});


const resolver = yupResolver(
    yup.object().shape({
      courseTitle: yup.string().required(),
      courseType: yup.string().required(),
      startDate: yup.date().required(),
      trainingUnits: yup.number().required(),
      maxParticipants: yup.number().required(),
      courseStatus: yup.string().required(),
      trainer: yup.number().required(),
      place: yup.number().required()
    }),
);

const queryClient = useQueryClient();

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


const {data: dateInfos, isLoading, isError: isHolidayError} = useQuery({
  queryKey: ['holidayInfo', addCourseDto.value.startDate, addCourseDto.value.trainingUnits],
  queryFn: () => getHolidayInfo(addCourseDto.value.startDate, addCourseDto.value.trainingUnits),
  enabled: computed(() => !!addCourseDto.value.startDate && !!addCourseDto.value.trainingUnits)
})

const invalidateDateCalulator = () => {
  queryClient.invalidateQueries({queryKey: ['holidayInfo']})
};

const {mutate: saveCourse, isPending, isSuccess, isError: isSaveCourseError} = useMutation({
  mutationFn: () =>
      apiClient.post("/api/course/create", {
        courseName: addCourseDto.value.courseTitle,
        startDateTime: addCourseDto.value.startDate,
        trainingUnits: addCourseDto.value.trainingUnits,
        courseOwnerId: addCourseDto.value.trainer,
        placeId: addCourseDto.value.place,
        numberOfMaxParticipants: addCourseDto.value.maxParticipants,
        courseTypeId: addCourseDto.value.courseType,
        courseStatus: addCourseDto.value.courseStatus
      }),
  onSuccess: () => {
    queryClient.invalidateQueries({queryKey: ["courses"]});
    open.value = false;
  }
});

const {
  mutate: saveCourseChanges,
  isPending: isChangePending,
  isSuccess: isChangeSuccess,
  isError: isChangeSaveCourseError
} = useMutation({
  mutationFn: () =>
      apiClient.post("/api/course/change", {
        courseName: addCourseDto.value.courseTitle,
        startDateTime: addCourseDto.value.startDate,
        trainingUnits: addCourseDto.value.trainingUnits,
        courseOwnerId: addCourseDto.value.trainer,
        placeId: addCourseDto.value.place,
        numberOfMaxParticipants: addCourseDto.value.maxParticipants,
        courseTypeId: addCourseDto.value.courseType,
        courseStatus: addCourseDto.value.courseStatus
      }),
  onSuccess: () => {
    queryClient.invalidateQueries({queryKey: ["courses"]});
    open.value = false;
  }
});


const onFormSubmit = (values) => {

  if (values.valid) {
    props.isChange ? saveCourseChanges() : saveCourse();
  }
};

const trainingStatus = ref([
  {name: 'In Planning', code: 'PLANNING'},
  {name: 'Open', code: 'OPEN'},
  {name: 'Active', code: 'ACTIVE'},
  {name: 'Completed', code: 'COMPLETED'},
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
    <Form
        v-slot="$form"
        :initialValues="addCourseDto"
        id="defaultRegistrationForm"
        :resolver="resolver"
        @submit="onFormSubmit"
        class="flex flex-col gap-4 justify-center items-center w-full">

      <h1 v-if="isChange" class="text-4xl font-bold">Kurs ändern</h1>
      <h1 v-else class="text-4xl font-bold">Kurs hinzufügen</h1>

      <InputText
          v-model="addCourseDto.courseTitle"
          placeholder="Kurs-Titel"
          class="w-full"
          name="courseTitle"
      />
      <Select
          class="w-full"
          v-model="addCourseDto.courseType"
          placeholder="Kurs Typ"
          :options="availableCourseTypes"
          option-label="courseTypeName"
          option-value="courseTypeId"
          name="courseType"
      />

      <div class="flex gap-4 md:flex-row flex-col w-full">
        <DatePicker
            @valueChange="invalidateDateCalulator"
            v-model="addCourseDto.startDate"
            showIcon
            fluid
            :showOnFocus="false"
            showTime
            hourFormat="24"
            placeholder="Start-Datum"
            dateFormat="dd.mm.yy"
            name="startDate"
        />
        <InputNumber @valueChange="invalidateDateCalulator" v-model="addCourseDto.trainingUnits" name="trainingUnits"
                     placeholder="Trainingseinheiten"/>
      </div>

      <CourseTimeFrameInfo v-model="dateInfos" :chosen-date="addCourseDto.startDate"/>

      <div class="flex gap-4 md:flex-row flex-col w-full">
        <InputNumber v-model="addCourseDto.maxParticipants" placeholder="Max Teilehmer (Bsp. 5-15)"
                     name="maxParticipants" class="w-full"/>

        <Select
            v-model="addCourseDto.courseStatus"
            placeholder="Course status"
            :options="trainingStatus"
            optionLabel="name"
            optionValue="code"
            class="w-full"
            name="courseStatus"
        />
      </div>

      <Select
          v-model="addCourseDto.trainer"
          optionLabel="trainerName"
          placeholder="Trainer"
          :options="availableTrainers"
          option-value="trainerId"
          name="trainer"
          class="w-full"
      />
      <Select
          class="w-full"
          v-model="addCourseDto.place"
          placeholder="Ort"
          :options="availablePlaces"
          option-label="name"
          option-value="id"
          name="place"
      />

      <div class="flex gap-4 w-full justify-center items-center">
        <Button @click="open = false" class="w-24 h-10 px-4 py-2">Zurück</Button>
        <Button type="submit" class="w-24 h-10 px-4 py-2">
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
          <p v-else>Speichern</p>
        </Button>
      </div>
    </Form>
  </Dialog>
</template>

<style scoped></style>
