<script setup>
import { MemberRegistrationStore } from "@/stores/MemberRegistrationStore.ts";

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
        @click="MemberRegistrationStore().removeExtraPersonForm(props.index)"
        type="button"
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
          :name="`morePersons[${index}].name`"
          label="name"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <InputText
              class="w-full"
              :inputId="`name-${index}`"
              :name="`morePersons[${index}].name`"
              v-model="extraModelValues.name"
            />
            <Message
              v-if="error?.message"
              severity="error"
              size="small"
              variant="simple"
            >
              {{ error.message }}
            </Message>
            <label :for="`name-${index}`">Name</label>
          </FloatLabel>
        </FormField>

        <FormField
          class="flex-1"
          :name="`morePersons[${index}].surename`"
          label="surename"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <InputText
              class="w-full"
              :inputId="`surename-${index}`"
              :name="`morePersons[${index}].surename`"
              v-model="extraModelValues.surename"
            />
            <Message
              v-if="error?.message"
              severity="error"
              size="small"
              variant="simple"
            >
              {{ error.message }}
            </Message>
            <label :for="`surename-${index}`">Nachname</label>
          </FloatLabel>
        </FormField>
      </div>

      <div class="flex gap-2">
        <FormField
          class="flex-1"
          :name="`morePersons[${index}].birthday`"
          label="Geburtstag"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <DatePicker
              class="w-full"
              :inputId="`birthday-${index}`"
              :name="`morePersons[${index}].birthday`"
              v-model="extraModelValues.birthday"
              dateFormat="dd.mm.yy"
              showIcon
              fluid
              iconDisplay="input"
            />
            <label :for="`birthday-${index}`">Geburtsdatum</label>
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
          :name="`morePersons[${index}].gender`"
          label="Gender"
          v-slot="{ error }"
        >
          <FloatLabel variant="in" class="w-full">
            <Select
              :inputId="`gender-${index}`"
              :name="`morePersons[${index}].gender`"
              v-model="extraModelValues.gender"
              :options="genders"
              class="w-full"
            />
            <label :for="`gender-${index}`">Geschlecht</label>
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
