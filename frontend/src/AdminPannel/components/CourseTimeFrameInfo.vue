<script setup lang="ts">
import {TrainingUnitsDto} from "@/service/DateService";
import {computed} from "vue";

const dto = defineModel({type: Object as () => TrainingUnitsDto});

const dates = computed(() => {

    if (dto.value === undefined) {
        return [];
    }

    console.log("dto", dto.value);

    return dto.value.dates;
})

const holidayDates = computed(() => {

    if (dto.value === undefined) {
        return [];
    }
    return dto.value.holidayDates;
})

const listHolidays = computed(() => {

    const holodayName = [];

    holidayDates.value.map(holiday => {
        holodayName.push(holiday.holidayName);
    })

    return holodayName.join(", ");

})

</script>

<template>

	<div>
		<div class="flex gap-4">
			<div>
				<div>From:</div>
				<div v-if="dates.length > 0">{{ (dates[0]) }}</div>
			</div>

			<div>
				<div>To:</div>
				<div v-if="dates.length > 0">{{ (dates[dates.length - 1]) }}</div>
			</div>
		</div>
		<div>
			<Message v-if="holidayDates.length > 0" severity="error">
				<div class="pi pi-exclamation-circle"/>
				There are holidays in this time frame:
				<span class="font-bold">
				{{ listHolidays }}
				</span>
			</Message>
		</div>

	</div>
</template>

<style scoped>

</style>