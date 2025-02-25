<script setup lang="ts">
import { ref, watch } from 'vue';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import MultipleSelector from '@/components/multipleSelector/MultipleSelector.vue';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { Color } from '@/models/new-patient/color.model';
import { Zones, type ExamResults, ZoneDisplayNames } from '@/models/diagnostic/exam.model';
import ClassicSelector from '@/components/classicSelector/ClassicSelector.vue';
import { faPenToSquare, faTrashCan } from '@fortawesome/free-regular-svg-icons';

const props = defineProps<{
  onValidation: (examResults: ExamResults[]) => void;
  onBack: () => void;
  modalTitle: string;
  possibleExams: Record<Zones, string[]>;
  currentExamResults: ExamResults[];
}>();

const emits = defineEmits<{
  (event: 'update:selectedZone', value: Zones): void;
  (event: 'update:selectedSigns', value: string[]): void;
}>();
const examResults = ref<ExamResults[]>([...props.currentExamResults]);
const selectedZone = ref<Zones>(Zones.FACE);
const selectedSigns = ref<string[]>([]);
const confirmGoBack = ref<boolean>(false);

const zoneOptions = Object.keys(props.possibleExams).map((value) => ({
  value,
  label: ZoneDisplayNames[value as Zones]
}));

watch(selectedZone, (newZone) => {
  emits('update:selectedZone', newZone);
});

watch(selectedSigns, (newSigns) => {
  emits('update:selectedSigns', newSigns);
});

watch(
  examResults,
  (newResults) => {
    confirmGoBack.value = JSON.stringify(props.currentExamResults) !== JSON.stringify(newResults);
  },
  { deep: true }
);

function updateSelectedZone(zone: Zones) {
  selectedZone.value = zone;
}

function handleAddExam() {
  if (!selectedZone.value || selectedSigns.value.length === 0) {
    return;
  }

  if (examResults.value.some((item) => item.zone === selectedZone.value)) {
    // TODO: Show a toast error instead of an alert
    alert('Cette zone est déjà renseignée !');
    return;
  }

  examResults.value.push({
    zone: selectedZone.value,
    signs: [...selectedSigns.value]
  });

  // Reset with a zone not already pushed in examResults
  selectedZone.value = Object.values(Zones).find(
    (zone) => !examResults.value.some((item) => item.zone === zone)
  ) as Zones;
  selectedSigns.value = [];
}

function handleDeleteExam(index: number) {
  examResults.value.splice(index, 1);
}

function handleEditExam(index: number) {
  const row = examResults.value[index];
  selectedZone.value = row.zone;
  selectedSigns.value = [...row.signs];
  examResults.value.splice(index, 1);
}

function handleOnValidation() {
  props.onValidation(examResults.value);
}
</script>

<template>
  <GenericModal
    :title="modalTitle"
    validationLabel="Valider"
    :headerColor="Color.Orange"
    :onValidation="handleOnValidation"
    :onBack="props.onBack"
    :confirmGoBack="confirmGoBack"
    width="800px"
  >
    <div class="flex w-full h-full gap-6">
      <div class="w-1/2 flex flex-col items-center">
        <h2 class="text-lg font-semibold mb-4">Sélectionner une zone</h2>

        <div class="relative flex justify-center mb-6">
          <img
            src="@/assets/human-body.jpg"
            alt="Silhouette"
            class="h-64 object-contain w-[100%]"
          />
          <div
            id="face"
            v-if="possibleExams[`${Zones.FACE}`]"
            @click="updateSelectedZone(Zones.FACE)"
            class="z-[1] zone top-[10px] left-[58px] w-8 h-10"
            :class="{ selected: selectedZone == Zones.FACE }"
          ></div>
          <div
            id="mouse"
            v-if="possibleExams[`${Zones.MOUSE}`]"
            @click="updateSelectedZone(Zones.MOUSE)"
            class="z-[2] zone top-[35px] left-[66px] w-4 h-2"
            :class="{ selected: selectedZone == Zones.MOUSE }"
          ></div>
          <div v-if="possibleExams[`${Zones.EARS}`]" @click="updateSelectedZone(Zones.EARS)">
            <div
              id="ears1"
              class="zone top-[20px] left-[192px] w-3 h-4"
              :class="{ selected: selectedZone == Zones.EARS }"
            ></div>
            <div
              id="ears2"
              class="zone top-[20px] left-[162px] w-3 h-4"
              :class="{ selected: selectedZone == Zones.EARS }"
            ></div>
          </div>
          <div v-if="possibleExams[`${Zones.EYES}`]" @click="updateSelectedZone(Zones.EYES)">
            <div
              id="eyes1"
              class="zone z-[2] top-[20px] left-[75px] w-3 h-3"
              :class="{ selected: selectedZone == Zones.EYES }"
            ></div>
            <div
              id="eyes2"
              class="zone z-[2] top-[20px] left-[61px] w-3 h-3"
              :class="{ selected: selectedZone == Zones.EYES }"
            ></div>
          </div>
          <div
            id="shoulders"
            v-if="possibleExams[`${Zones.SHOULDERS}`]"
            @click="updateSelectedZone(Zones.SHOULDERS)"
            class="zone top-[52px] left-[40px] w-7 h-6"
            :class="{ selected: selectedZone == Zones.SHOULDERS }"
          ></div>
          <div
            id="arms"
            v-if="possibleExams[`${Zones.ARMS}`]"
            @click="updateSelectedZone(Zones.ARMS)"
            class="zone top-[76px] left-[33px] w-4 h-14 rotate-12"
            :class="{ selected: selectedZone == Zones.ARMS || selectedZone == Zones.MEMBERS }"
          ></div>
          <div
            id="hands"
            v-if="possibleExams[`${Zones.HANDS}`]"
            @click="updateSelectedZone(Zones.HANDS)"
            class="zone top-[133px] left-[21px] w-6 h-6"
            :class="{ selected: selectedZone == Zones.HANDS }"
          ></div>
          <div
            id="chest"
            v-if="possibleExams[`${Zones.CHEST}`]"
            @click="updateSelectedZone(Zones.CHEST)"
            class="zone top-[72px] left-[54px] w-10 h-7"
            :class="{ selected: selectedZone == Zones.CHEST }"
          ></div>
          <div
            id="abdomen"
            v-if="possibleExams[`${Zones.ABDOMEN}`]"
            @click="updateSelectedZone(Zones.ABDOMEN)"
            class="zone top-[100px] left-[56px] w-9 h-6"
            :class="{ selected: selectedZone == Zones.ABDOMEN }"
          ></div>
          <div
            id="genitals"
            v-if="possibleExams[`${Zones.GENITALS}`]"
            @click="updateSelectedZone(Zones.GENITALS)"
            class="zone top-[125px] left-[58px] w-8 h-5"
            :class="{ selected: selectedZone == Zones.GENITALS }"
          ></div>
          <div
            id="legs"
            v-if="possibleExams[`${Zones.LEGS}`]"
            @click="updateSelectedZone(Zones.LEGS)"
            class="zone top-[150px] left-[55px] w-5 h-[75px] rotate-[175deg]"
            :class="{ selected: selectedZone == Zones.LEGS || selectedZone == Zones.MEMBERS }"
          ></div>
          <div
            id="feets"
            v-if="possibleExams[`${Zones.FEET}`]"
            @click="updateSelectedZone(Zones.FEET)"
            class="zone top-[228px] left-[163px] w-10 h-5"
            :class="{ selected: selectedZone == Zones.FEET }"
          ></div>
          <div
            id="neck"
            v-if="possibleExams[`${Zones.NECK}`]"
            @click="updateSelectedZone(Zones.NECK)"
            class="zone top-[38px] left-[167px] w-8 h-5"
            :class="{ selected: selectedZone == Zones.NECK }"
          ></div>
          <div
            id="back"
            v-if="possibleExams[`${Zones.BACK}`]"
            @click="updateSelectedZone(Zones.BACK)"
            class="zone top-[58px] left-[163px] w-10 h-16"
            :class="{ selected: selectedZone == Zones.BACK }"
          ></div>
          <div
            id="hairs"
            v-if="possibleExams[`${Zones.HAIRS}`]"
            @click="updateSelectedZone(Zones.HAIRS)"
            class="zone top-[10px] left-[169px] w-7 h-4"
            :class="{ selected: selectedZone == Zones.HAIRS }"
          ></div>
          <div
            id="elbow"
            v-if="possibleExams[`${Zones.ELBOWS}`]"
            @click="updateSelectedZone(Zones.ELBOWS)"
            class="zone top-[90px] left-[142px] w-5 h-6"
            :class="{ selected: selectedZone == Zones.ELBOWS }"
          ></div>
        </div>

        <div class="bg-white border border-gray-200 rounded-md p-4 shadow-sm w-[350px]">
          <div class="mb-4">
            <label for="zone" class="block font-semibold text-sm mb-1">Zone sélectionnée : </label>
            <ClassicSelector id="zone" :options="zoneOptions" v-model="selectedZone" />
          </div>

          <div>
            <label class="block font-semibold text-sm mb-1">{{ modalTitle }} :</label>
            <MultipleSelector
              v-model="selectedSigns"
              :options="possibleExams[`${selectedZone}`] || []"
            />
          </div>

          <div class="mt-4">
            <ActionButton label="OK" :onClick="handleAddExam" :color="Color.Grey" />
          </div>
        </div>
      </div>

      <div class="w-1/2 flex flex-col">
        <h2 class="text-lg font-semibold mb-4 text-center">Récapitulatif des résultats</h2>
        <table class="w-[350px] border-collapse">
          <thead>
            <tr class="border-b border-gray-300">
              <th class="text-left py-2 w-1/4 font-bold">Zone</th>
              <th class="text-left py-2 w-1/2 font-bold">{{ modalTitle }}</th>
              <th class="text-center py-2 w-1/4 font-bold">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in examResults" :key="index" class="border-b border-gray-100">
              <td class="py-3 px-2">{{ ZoneDisplayNames[item.zone] }}</td>
              <td class="py-3 px-2">{{ item.signs.join(', ') }}</td>
              <td class="py-3 px-2 flex justify-center gap-2">
                <ActionButton
                  :icon="faPenToSquare"
                  :color="Color.Grey"
                  iconOnly
                  size="small"
                  @click="handleEditExam(index)"
                />
                <ActionButton
                  :icon="faTrashCan"
                  :color="Color.Grey"
                  iconOnly
                  size="small"
                  @click="handleDeleteExam(index)"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </GenericModal>
</template>

<style scoped>
.selected {
  background-color: rgb(241, 191, 109) !important;
}

.zone {
  border-radius: 9999px;
  position: absolute;
  opacity: 50%;
  cursor: pointer;
  background-color: #d8d8d8;
}
</style>
