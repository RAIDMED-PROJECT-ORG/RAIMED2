<script setup lang="ts">
import { ref, watch } from 'vue';
import GenericModal from '@/components/modal/genericModal/GenericModal.vue';
import MultipleSelector from '@/components/multipleSelector/MultipleSelector.vue';
import ActionButton from '@/components/actionButton/ActionButton.vue';
import { faPen, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { Color } from '@/models/new-patient/color.model';
import { Zones, type ExamResults } from '@/models/diagnostic/exam.model';
import ClassicSelector from '@/components/classicSelector/ClassicSelector.vue';

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
const switchConfirmGoBackModalVisibility = ref(false);

const iconEdit = faPen;
const iconDelete = faTrashCan;

watch(selectedZone, (newZone) => {
  emits('update:selectedZone', newZone);
});

watch(selectedSigns, (newSigns) => {
  emits('update:selectedSigns', newSigns);
});

function updateSelectedZone(zone: Zones) {
  console.log(zone);
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

function handleOnBack() {
  if (JSON.stringify(props.currentExamResults) !== JSON.stringify(examResults.value)) {
    switchConfirmGoBackModalVisibility.value = true;
    return;
  }

  props.onBack();
}

function handleGoBackValidation() {
  switchConfirmGoBackModalVisibility.value = false;
  props.onBack();
}

function handleGoBackCancel() {
  switchConfirmGoBackModalVisibility.value = false;
}
</script>

<template>
  <GenericModal
    :title="modalTitle"
    validationLabel="Valider"
    :headerColor="Color.Orange"
    :onValidation="handleOnValidation"
    :onBack="handleOnBack"
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
            @click="updateSelectedZone(Zones.FACE)"
            class="z-[1] zone top-[10px] left-[58px] w-8 h-10"
            :class="{ selected: selectedZone == Zones.FACE }"
          ></div>
          <div
            id="mouse"
            @click="updateSelectedZone(Zones.MOUSE)"
            class="z-[2] zone top-[35px] left-[66px] w-4 h-2"
            :class="{ selected: selectedZone == Zones.MOUSE }"
          ></div>
          <div @click="updateSelectedZone(Zones.EARS)">
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
          <div @click="updateSelectedZone(Zones.EYES)">
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
            @click="updateSelectedZone(Zones.SHOULDERS)"
            class="zone top-[52px] left-[40px] w-7 h-6"
            :class="{ selected: selectedZone == Zones.SHOULDERS }"
          ></div>
          <div
            id="arms"
            @click="updateSelectedZone(Zones.ARMS)"
            class="zone top-[76px] left-[33px] w-4 h-14 rotate-12"
            :class="{ selected: selectedZone == Zones.ARMS || selectedZone == Zones.MEMBERS }"
          ></div>
          <div
            id="hands"
            @click="updateSelectedZone(Zones.HANDS)"
            class="zone top-[133px] left-[21px] w-6 h-6"
            :class="{ selected: selectedZone == Zones.HANDS }"
          ></div>
          <div
            id="chest"
            @click="updateSelectedZone(Zones.CHEST)"
            class="zone top-[72px] left-[54px] w-10 h-7"
            :class="{ selected: selectedZone == Zones.CHEST }"
          ></div>
          <div
            id="abdomen"
            @click="updateSelectedZone(Zones.ABDOMEN)"
            class="zone top-[100px] left-[56px] w-9 h-6"
            :class="{ selected: selectedZone == Zones.ABDOMEN }"
          ></div>
          <div
            id="genitals"
            @click="updateSelectedZone(Zones.GENITALS)"
            class="zone top-[125px] left-[58px] w-8 h-5"
            :class="{ selected: selectedZone == Zones.GENITALS }"
          ></div>
          <div
            id="legs"
            @click="updateSelectedZone(Zones.LEGS)"
            class="zone top-[150px] left-[55px] w-5 h-[75px] rotate-[175deg]"
            :class="{ selected: selectedZone == Zones.LEGS || selectedZone == Zones.MEMBERS }"
          ></div>
          <div
            id="feets"
            @click="updateSelectedZone(Zones.FEET)"
            class="zone top-[228px] left-[163px] w-10 h-5"
            :class="{ selected: selectedZone == Zones.FEET }"
          ></div>
          <div
            id="neck"
            @click="updateSelectedZone(Zones.NECK)"
            class="zone top-[38px] left-[167px] w-8 h-5"
            :class="{ selected: selectedZone == Zones.NECK }"
          ></div>
          <div
            id="back"
            @click="updateSelectedZone(Zones.BACK)"
            class="zone top-[58px] left-[163px] w-10 h-16"
            :class="{ selected: selectedZone == Zones.BACK }"
          ></div>
          <div
            id="hairs"
            @click="updateSelectedZone(Zones.HAIRS)"
            class="zone top-[10px] left-[169px] w-7 h-4"
            :class="{ selected: selectedZone == Zones.HAIRS }"
          ></div>
          <div
            id="elbow"
            @click="updateSelectedZone(Zones.ELBOWS)"
            class="zone top-[90px] left-[142px] w-5 h-6"
            :class="{ selected: selectedZone == Zones.ELBOWS }"
          ></div>
        </div>

        <div class="bg-white border border-gray-200 rounded-md p-4 shadow-sm w-[350px]">
          <div class="mb-4">
            <label class="block font-semibold text-sm mb-1">Zone sélectionnée : </label>
            <ClassicSelector :options="Object.values(Zones)" v-model="selectedZone" />
          </div>

          <div>
            <label class="block font-semibold text-sm mb-1">{{ modalTitle }} :</label>
            <MultipleSelector v-model="selectedSigns" :options="possibleExams[`${selectedZone}`]" />
          </div>

          <div class="mt-4">
            <ActionButton label="OK" :onClick="handleAddExam" :color="Color.Grey" />
          </div>
        </div>
      </div>

      <div class="w-1/2 flex flex-col">
        <h2 class="text-lg font-semibold mb-4">Récapitulatif des résultats</h2>
        <table class="w-[350px] border-collapse">
          <thead>
            <tr class="border-b border-gray-300">
              <th class="text-left py-2 w-1/4">Zone</th>
              <th class="text-left py-2 w-1/2">{{ modalTitle }}</th>
              <th class="text-center py-2 w-1/4">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in examResults" :key="index" class="border-b border-gray-100">
              <td class="py-3 px-2">{{ item.zone }}</td>
              <td class="py-3 px-2">{{ item.signs.join(', ') }}</td>
              <td class="py-3 px-2 flex justify-center gap-2">
                <ActionButton
                  :icon="iconEdit"
                  :color="Color.Grey"
                  iconOnly
                  size="small"
                  @click="handleEditExam(index)"
                />
                <ActionButton
                  :icon="iconDelete"
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
  <GenericModal
    v-if="switchConfirmGoBackModalVisibility.valueOf()"
    title="Annulation"
    :headerColor="Color.Orange"
    validationLabel="Confirmer"
    :onValidation="handleGoBackValidation"
    :onBack="handleGoBackCancel"
  >
    <p class="mx-5 text-base">Des modifications n'ont pas été sauvegardés.</p>
    <p class="text-base text-center">Êtes-vous sûr de vouloir annuler ?</p>
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