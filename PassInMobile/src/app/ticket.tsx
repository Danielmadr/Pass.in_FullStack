import { Credential } from "@/components/credential";
import { FontAwesome, AntDesign } from "@expo/vector-icons";
import { Header } from "@/components/header";
import {
  Text,
  View,
  Alert,
  Modal,
  Share,
  StatusBar,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { MotiView } from "moti";
import { colors } from "@/styles/colors";
import { Button } from "@/components/button";

import { useState } from "react";
import * as ImagePicker from "expo-image-picker";
import { QRCode } from "@/components/qrcode";
import { Redirect } from "expo-router";

import { useBadgeStore } from "@/store/badge-store";

export default function Ticket() {
  const [AmplifyQRCode, setAmplifyQRCode] = useState(false);

  const badgeStore = useBadgeStore();

  async function handleShare() {
    try {
      if (badgeStore.data?.checkInUrl) {
        await Share.share({
          message: badgeStore.data?.checkInUrl,
        });
      }
    } catch (error) {
      console.log(error);
      Alert.alert("Atenção", "Falha ao compartilhar");
    }
  }

  async function handleSelectImage() {
    try {
      const result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Images,
        allowsEditing: true,
        aspect: [4, 4],
        quality: 1,
      });

      if (result.assets) {
        badgeStore.updateAvatar(result.assets[0].uri);
      }
    } catch (error) {
      console.log(error);
      Alert.alert("Atenção", "Falha ao selecionar imagem");
    }
  }

  if (!badgeStore.data?.checkInUrl) {
    return <Redirect href={"/"} />;
  }

  return (
    <View className="flex-1 bg-green-500">
      <StatusBar barStyle="light-content" />
      <Header title="Minha Credencial" />

      <ScrollView
        className="-mt-28 -z-10"
        showsVerticalScrollIndicator={false}
        contentContainerClassName="px-8 pb-8"
      >
        <Credential
          data={badgeStore.data}
          onChangeAvatar={handleSelectImage}
          onAmplifyQRCode={() => setAmplifyQRCode(true)}
        />

        <MotiView
          from={{ translateY: 0 }}
          animate={{ translateY: 15 }}
          transition={{ loop: true, type: "timing", duration: 700 }}
        >
          <FontAwesome
            name="angle-double-down"
            size={24}
            color={colors.gray[300]}
            className="self-center my-6"
          />
        </MotiView>

        <Text className="text-white font-bold text-2xl mt-4">
          Compartilhar credencial
        </Text>

        <Text className="text-white font-regular text-base mt-1 mb-6">
          Mostre ao mundo que você vai participar do evento{" "}
          {badgeStore.data.eventTitle}!
        </Text>

        <Button title="Compartilhar" onPress={handleShare} />

        <TouchableOpacity
          activeOpacity={0.7}
          className="mt-10"
          onPress={() => badgeStore.reset()}
        >
          <Text className="text-white text-base font-bold text-center">
            Trocar Credencial
          </Text>
        </TouchableOpacity>
      </ScrollView>

      <Modal
        visible={AmplifyQRCode}
        statusBarTranslucent
        transparent
        animationType="fade"
        className=" items-center justify-center"
      >
        <View
          style={{
            flex: 1,
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "rgba(0, 0, 0, 0.5)",
          }}
        >
          <View className="bg-green-500 items-center justify-center">
            <QRCode value={badgeStore.data?.checkInUrl} size={300} />
          </View>
          <TouchableOpacity
            activeOpacity={0.7}
            onPress={() => setAmplifyQRCode(false)}
            className=" h-10 mt-6 items-center justify-center"
          >
            <AntDesign name="closecircleo" size={30} color="orange" />
          </TouchableOpacity>
        </View>
      </Modal>
    </View>
  );
}
