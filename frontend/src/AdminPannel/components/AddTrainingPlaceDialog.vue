<script setup lang="ts">

import {Form} from "@primevue/forms";
import {onMounted, ref} from "vue";
import {yupResolver} from "@primevue/forms/resolvers/yup";
import * as yup from "yup";
import {useMutation, useQueryClient} from "@tanstack/vue-query";
import {apiClient} from "@/apiClient.js";


const open = defineModel<boolean>({required: true});

const props = defineProps<{
  isChange?: boolean,
  name?: string,
  street?: string,
  houseNumber?: string,
  streetNumberAddition?: string,
  city?: number,
  state?: string,
  country?: string,
  postalCode?: string,
}>();


const addPlaceDto = ref({
  name: "",
  street: "",
  houseNumber: "",
  streetNumberAddition: "",
  city: "",
  state: "",
  country: "",
  postalCode: "",
})

onMounted(() => {
  addPlaceDto.value.name = props.name ?? "";
  addPlaceDto.value.street = props.street ?? "";
  addPlaceDto.value.houseNumber = props.houseNumber ?? "";
  addPlaceDto.value.streetNumberAddition = props.streetNumberAddition ?? "";
  addPlaceDto.value.city = props.city ?? "";
  addPlaceDto.value.postalCode = props.postalCode ?? "";
});

const resolver = yupResolver(
    yup.object().shape({
      name: yup.string().required(),
      street: yup.string().required(),
      houseNumber: yup.string().required(),
      streetNumberAddition: yup.string(),
      city: yup.string().required(),
      postalCode: yup.number(),
    }),
);

const queryClient = useQueryClient();


const {mutate: saveTrainingPlace, isPending, isSuccess, isError: isSavePlaceError} = useMutation({
  mutationFn: () =>
      apiClient.post("/api/places", {
        name: addPlaceDto.value.name,
        street: addPlaceDto.value.street,
        houseNumber: addPlaceDto.value.houseNumber,
        streetNumberAddition: addPlaceDto.value.streetNumberAddition,
        city: addPlaceDto.value.city,
        state: addPlaceDto.value.state,
      }),
  onSuccess: () => {
    queryClient.invalidateQueries({queryKey: ["trainingPlace"]});
    open.value = false;
  }
});

const {
  mutate: saveTrainingPlaceChanges,
  isPending: isChangePlacePending,
  isSuccess: isChangePlaceSuccess,
  isError: isChangeSavePlaceError
} = useMutation({
  mutationFn: () =>
      apiClient.post("/api/places", {
        name: addPlaceDto.value.name,
        street: addPlaceDto.value.street,
        houseNumber: addPlaceDto.value.houseNumber,
        streetNumberAddition: addPlaceDto.value.streetNumberAddition,
        city: addPlaceDto.value.city,
        state: addPlaceDto.value.state,
      }),
  onSuccess: () => {
    queryClient.invalidateQueries({queryKey: ["trainingPlace"]});
    open.value = false;
  }
});


const onFormSubmit = (values) => {

  if (values.valid) {
    props.isChange ? saveTrainingPlaceChanges() : saveTrainingPlace();
  }
};

const trainingStatus = ref([
  {name: 'Open', code: 'CLOSED'},
  {name: 'Closed', code: 'OPEN'},
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
        :initialValues="addPlaceDto"
        id="defaultRegistrationForm"
        :resolver="resolver"
        @submit="onFormSubmit"
        class="flex flex-col gap-4 justify-center items-center w-full">

      <h1 v-if="isChange" class="text-4xl font-bold">Ort ändern</h1>
      <h1 v-else class="text-4xl font-bold">Ort hinzufügen</h1>

      <InputText
          v-model="addPlaceDto.name"
          placeholder="Trainingsort Name"
          class="w-full"
          name="name"
      />

      <div class="flex gap-4 md:flex-row flex-col w-full">
        <InputText
            v-model="addPlaceDto.street"
            placeholder="Straße"
            class="w-[70%]"
            name="street"
        />
        <InputText
            v-model="addPlaceDto.houseNumber"
            placeholder="Hausnummer"
            class="w-[30%]"
            name="houseNumber"
        />
      </div>

      <InputText
          v-model="addPlaceDto.streetNumberAddition"
          placeholder="Address-Zusatz*"
          class="w-full"
          name="streetNumberAddition"
      />

      <div class="flex gap-4 md:flex-row flex-col w-full">
        <InputText
            v-model="addPlaceDto.city"
            placeholder="Stadt"
            class="w-full"
            name="city"
        />

        <InputText
            v-model="addPlaceDto.postalCode"
            placeholder="Postleitzahl"
            class="w-full"
            name="postalCode"
        />

      </div>

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

<style scoped>

</style>