<script setup lang="ts">
import { TrainingUnitsDto } from "@/service/DateService";
import { computed } from "vue";
import { formatDate } from "@/utils/dateUtil";

const dto = defineModel({ type: Object as () => TrainingUnitsDto });

const dates = computed(() => {
  if (dto.value === undefined) {
    return [];
  }

  return dto.value.dates;
});

const holidayDates = computed(() => {
  if (dto.value === undefined) {
    return [];
  }
  return dto.value.holidayDates;
});
</script>

<template>
  <div class="flex flex-col gap-2 text-center">
    <div class="flex gap-8 justify-around items-center">
      <div class="flex flex-col justify-center items-center font-bold">
        <div class="flex justify-between items-center flex-row">From:</div>
        <div v-if="dates.length > 0">{{ formatDate(dates[0]) }}</div>
      </div>

      <div class="flex flex-col justify-center items-center font-bold">
        <div class="flex justify-between items-center flex-row">To:</div>
        <div v-if="dates.length > 0">
          {{ formatDate(dates[dates.length - 1]) }}
        </div>
      </div>
    </div>
    <div>
      <Message
        v-for="holiday in holidayDates"
        v-if="holidayDates.length > 0"
        severity="error"
      >
        <div class="flex gap-2 justify-center items-center">
          <div class="pi pi-exclamation-circle" />
          <div>
            <span class="font-bold">
              {{ holiday.holidayName }}
            </span>
            from
            <span class="font-bold">{{ formatDate(holiday.fromDate) }}</span> -
            <span class="font-bold">{{ formatDate(holiday.fromDate) }}</span>
          </div>
        </div>
      </Message>
    </div>
  </div>
</template>

<style scoped></style>
