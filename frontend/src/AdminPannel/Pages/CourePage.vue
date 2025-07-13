<script setup lang="ts">
import {Button} from 'primevue'
import CourseCard from "@/AdminPannel/components/CourseCard.vue";
import {ref} from "vue";

const courses = ref([
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/NonSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/TeenSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/MasterSwim.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/TeenSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
    {
        title: "Nichtschimmer Kurs",
        imagePath: "/images/TeenSwimPic.jpg",
        trainer: "Kevin Krüger",
        status: "Aktiv",
        fromDate: "01.01.2022",
        toDate: "02.01.2022",
        totalUnits: 15,
        unitsDone: 0,
        place: "Firtz-Erler Bad",
        participants: 0,
        maxParticipants: 15
    },
]);

</script>

<template>
	<div class="flex flex-col h-full">
		<div class="flex justify-between mx-8 mt-4">
			<h2 class="text-2xl font-bold">Courses</h2>
			<Button label="Add Course" icon="pi pi-plus"/>
		</div>

		<div class="flex-1 m-4 overflow-auto">
			<div class="bg-bsc-gray overflow-scroll rounded-lg h-full p-4 flex flex-wrap gap-x-4 gap-y-4 justify-center items-center">

				<CourseCard v-for="course of courses"
				            :title="course.title"
				            :imagePath="course.imagePath"
				            :trainer="course.trainer"
				            :status="course.status"
				            :fromDate="course.fromDate"
				            :toDate="course.toDate"
				            :totalUnits="course.totalUnits"
				            :unitsDone="course.unitsDone"
				            :place="course.place"
				            :participants="course.participants"
				            :maxParticipants="course.maxParticipants"
				/>
			</div>
		</div>
	</div>
</template>

<style scoped>

</style>