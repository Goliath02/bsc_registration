<script setup>
import { useRegistrationStore } from "@/stores/RegistrationStore.js";

const extraModelValues = defineModel();

const props = defineProps({
  index: Number,
});

const genders = ["Männlich", "Weiblich", "Divers"];
</script>

<template>
  <div class="bg-bsc-lightgray p-[1em] rounded-lg">
    <div class="flex justify-between">
      <h3 class="font-bold text-[1.2em]">Extra Person {{ props.index + 1 }}</h3>
      <button
        class="fill-stone-400 hover:fill-stone-200 hover:cursor-pointer"
        @click="useRegistrationStore().removeExtraPersonForm(props.index)"
      >
        <svg
          class="bi bi-x-circle-fill"
          fill="currentColor"
          height="32"
          viewBox="0 0 16 16"
          width="32"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293z"
          />
        </svg>
      </button>
    </div>

    <div class="flex flex-col gap-2">
      <div class="flex gap-2">
        <FormField
          class="flex-1"
          :name="`morePersons[${index}].extraName`"
          label="extraName"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <InputText
              class="w-full"
              :inputId="`extraName-${index}`"
              :name="`morePersons[${index}].extraName`"
              v-model="extraModelValues.extraName"
            />
            <Message
              v-if="error?.message"
              severity="error"
              size="small"
              variant="simple"
            >
              {{ error.message }}
            </Message>
            <label :for="`extraName-${index}`">Name</label>
          </FloatLabel>
        </FormField>

        <FormField
          class="flex-1"
          :name="`morePersons[${index}].extraSureName`"
          label="extraSureName"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <InputText
              class="w-full"
              :inputId="`extraSureName-${index}`"
              :name="`morePersons[${index}].extraSureName`"
              v-model="extraModelValues.extraSureName"
            />
            <Message
              v-if="error?.message"
              severity="error"
              size="small"
              variant="simple"
            >
              {{ error.message }}
            </Message>
            <label :for="`extraSureName-${index}`">Nachname</label>
          </FloatLabel>
        </FormField>
      </div>

      <div class="flex gap-2">
        <FormField
          class="flex-1"
          :name="`morePersons[${index}].extraBirthday`"
          label="Geburtstag"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <DatePicker
              class="w-full"
              :inputId="`extraBirthday-${index}`"
              :name="`morePersons[${index}].extraBirthday`"
              v-model="extraModelValues.extraBirthday"
              dateFormat="dd.mm.yy"
              showIcon
              fluid
              iconDisplay="input"
            />
            <label :for="`extraBirthday-${index}`">Geburtsdatum</label>
            <Message
              v-if="error?.message"
              severity="error"
              size="small"
              variant="simple"
            >
              {{ error.message }}
            </Message>
          </FloatLabel>
        </FormField>

        <FormField
          class="flex-1"
          :name="`morePersons[${index}].extraGender`"
          label="Gender"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <Select
              :inputId="`extraGender-${index}`"
              :name="`morePersons[${index}].extraGender`"
              v-model="extraModelValues.extraGender"
              :options="genders"
              class="w-full"
            />
            <label :for="`extraGender-${index}`">Geschlecht</label>
            <Message
              v-if="error?.message"
              severity="error"
              size="small"
              variant="simple"
            >
              {{ error.message }}
            </Message>
          </FloatLabel>
        </FormField>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
